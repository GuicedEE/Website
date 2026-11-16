from pathlib import Path
import re,csv,json,zipfile,collections,os
root=Path(__file__).resolve().parents[5]
out=Path(os.environ.get('GUICEDEE_CONFIGURATION_INVENTORY_OUTPUT', root/'GuicedEE/website/docs/reviews/vertx-5.2.3'));out.mkdir(parents=True,exist_ok=True)
modules=['vertx','web','metrics','telemetry']
sitepath=root/'GuicedEE/website/src/main/java/com/guicedee/website/pages/EnvironmentVariablesPage.java'
site=sitepath.read_text(encoding='utf-8')
website={m[1]:{'description':m[2],'default':m[3],'line':site[:m.start()].count('\n')+1} for m in re.finditer(r'\{\s*"([A-Z][A-Z0-9_<>a-z]+)"\s*,\s*"([^"]*)"\s*,\s*"([^"]*)"',site)}
def mask_comments(s):
 return re.sub(r'"(?:\\.|[^"\\])*"|//[^\n]*|/\*[\s\S]*?\*/', lambda m: m[0] if m[0].startswith('"') else ''.join('\n' if c=='\n' else ' ' for c in m[0]), s)
# Source files remain untouched. Inventories record executable lookups and their exact anchors.
consumers={};annotations=[];codefiles=[]
def add(key,module,path,line,mechanism):
 if key not in consumers: consumers[key]={'module':module,'references':[],'mechanism':set()}
 consumers[key]['references'].append(f'{path.relative_to(root).as_posix()}:{line}')
 consumers[key]['mechanism'].add(mechanism)
for module in modules:
 for p in sorted((root/'GuicedEE'/module/'src/main/java').rglob('*.java')):
  raw=p.read_text(encoding='utf-8');s=mask_comments(raw);codefiles.append((module,p,s))
  for m in re.finditer(r'(?:getSystemPropertyOrEnvironment|getProperty|getenv)\(\s*"([A-Z][A-Z0-9_]+)"\s*[,)]',s):
   add(m[1],module,p,s[:m.start()].count('\n')+1,'literal lookup')
  for m in re.finditer(r'resolve\([^\n]*?,\s*"((?:REDIS|GRPC)_[A-Z_]+)"',s):
   add(m[1],module,p,s[:m.start()].count('\n')+1,'annotation resolver')
  for m in re.finditer(r'resolveOption\(address,\s*"(VERTX_EVENT_[A-Z_]+)"',s):
   line=s[:m.start()].count('\n')+1
   add(m[1],module,p,line,'global event option')
   add(m[1]+'_<ADDR>',module,p,line,'per-address system property / OS environment only')
  prefixes=re.findall(r'getSystemPropertyOrEnvironment\("(VERTX_AUTH_[A-Z0-9_]*?)"\s*\+',s)
  for prefix in set(prefixes):
   for m in re.finditer(r'\b(?:env|resolveString)\(\s*"([A-Z][A-Z0-9_]+)"\s*,',s):
    add(prefix+m[1],module,p,s[:m.start()].count('\n')+1,'provider prefix + suffix')
   if 'LDAP_' in prefix:
    for m in re.finditer(r'resolve\(\s*"([A-Z_]+)"',s): add(prefix+m[1],module,p,s[:m.start()].count('\n')+1,'provider prefix + suffix')
  for m in re.finditer(r'resolveSignalEndpoint\(\s*"([A-Z_]+)"',s):
   add(m[1],module,p,s[:m.start()].count('\n')+1,'per-signal endpoint resolver')
  # Cross-provider use of the JWT resolver and indexed PEM keys.
  for m in re.finditer(r'JwtAuthenticationProvider.env\("([A-Z_]+)"',s):add('VERTX_AUTH_JWT_'+m[1],module,p,s[:m.start()].count('\n')+1,'JWT provider resolver')
  for m in re.finditer(r'env\("PUBSECKEY_"\s*\+\s*i\s*\+\s*"(_[A-Z_]+)"',s):add('VERTX_AUTH_PUBSECKEY_<INDEX>'+m[1],module,p,s[:m.start()].count('\n')+1,'indexed PEM override')
  if p.name=='VertxEventRegistry.java': add('VERTX_EVENT_ADDRESS_<ADDR>',module,p,101,'per-address system property / OS environment only')
  for ann in re.finditer(r'@interface\s+(\w+)\s*\{',s):
   start=ann.end();depth=1;end=start
   while depth and end<len(s):
    if s[end]=='{':depth+=1
    elif s[end]=='}':depth-=1
    end+=1
   body=s[start:end-1]
   for m in re.finditer(r'(?m)^\s*(?:public\s+)?([\w.]+(?:\[\])?)\s+(\w+)\(\)\s*(?:default\s+([^;]+))?;',body):
    # A nested annotation owns its members, not the enclosing annotation.
    prior=body[:m.start()]
    if prior.count('{')!=prior.count('}'):continue
    annotations.append({'module':module,'annotation':ann[1],'attribute':m[2],'type':m[1],
      'default':re.sub(r'\s+',' ',m[3].strip()) if m[3] else '(required)',
      'source':f'{p.relative_to(root).as_posix()}:{s[:start+m.start()].count(chr(10))+1}'})
# Preserve explicit limits: this is key presence, not proof of correct default or working behavior.
rows=[]
for key,entry in sorted(consumers.items()):
 doc=website.get(key)
 rows.append({'module':entry['module'],'environment_key':key,'website_status':'present' if doc else 'missing',
  'website_default':doc['default'] if doc else '', 'website_line':doc['line'] if doc else '',
  'mechanism':' | '.join(sorted(entry['mechanism'])),'runtime_sources':' | '.join(sorted(set(entry['references'])))})
for filename,data in [('annotation-members.csv',annotations),('environment-website.csv',rows)]:
 with (out/filename).open('w',newline='',encoding='utf-8') as f:
  w=csv.DictWriter(f,fieldnames=list(data[0]));w.writeheader();w.writerows(data)
summary={'scope':modules,'annotation_types':len(set((a['module'],a['annotation']) for a in annotations)),
 'annotation_members':len(annotations),'environment_keys_and_templates':len(rows),
 'website_present':sum(r['website_status']=='present' for r in rows),'website_missing':sum(r['website_status']=='missing' for r in rows),
 'modules':{m:{'annotation_members':sum(a['module']==m for a in annotations),'environment_keys_and_templates':sum(r['module']==m for r in rows),'website_present':sum(r['module']==m and r['website_status']=='present' for r in rows)} for m in modules}}
prefixes=('VERTX_','HTTP_','HTTPS_','HTTP2_','GRPC_','REDIS_','METRICS_','TELEMETRY_','OTEL_')
summary['website_keys_without_consumer_in_scope']=[k for k in website if k.startswith(prefixes) and k not in consumers]
(out/'inventory-summary.json').write_text(json.dumps(summary,indent=2))
print(json.dumps(summary,indent=2))
