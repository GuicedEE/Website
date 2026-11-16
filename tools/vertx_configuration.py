"""Generate the 5.2.3 configuration reference; --check fails on source/document drift.

Run from any directory. No Java builds, network, or application configuration are read.
Annotation declarations and executable lookups are inventoried; explicit mappings below
record semantic aliases, activation and unsupported metadata. Runtime tests verify setters.
"""
from pathlib import Path
import contextlib, csv, io, json, os, re, runpy, sys, tempfile

ROOT = Path(__file__).resolve().parents[3]
SITE = ROOT / 'GuicedEE/website'
CHECK = '--check' in sys.argv

def inventory():
    with tempfile.TemporaryDirectory(prefix='guicedee-config-inventory-') as tmp:
        previous = os.environ.get('GUICEDEE_CONFIGURATION_INVENTORY_OUTPUT')
        try:
            os.environ['GUICEDEE_CONFIGURATION_INVENTORY_OUTPUT'] = tmp
            with contextlib.redirect_stdout(io.StringIO()):
                runpy.run_path(str(SITE / 'docs/reviews/vertx-5.2.3/inventory.py'))
            members = list(csv.DictReader((Path(tmp) / 'annotation-members.csv').open(encoding='utf-8')))
            keys = {r['environment_key']: r for r in csv.DictReader((Path(tmp) / 'environment-website.csv').open(encoding='utf-8'))}
            return members, keys
        finally:
            if previous is None: os.environ.pop('GUICEDEE_CONFIGURATION_INVENTORY_OUTPUT', None)
            else: os.environ['GUICEDEE_CONFIGURATION_INVENTORY_OUTPUT'] = previous

PREFIXES = {
    'AbacOptions': 'VERTX_AUTH_ABAC_', 'AuthKeyStore': 'VERTX_AUTH_KEYSTORE_',
    'AuthOptions': 'VERTX_AUTH_', 'AuthPubSecKey': 'VERTX_AUTH_PUBSECKEY_<INDEX>_',
    'HtdigestAuthOptions': 'VERTX_AUTH_HTDIGEST_', 'HtpasswdAuthOptions': 'VERTX_AUTH_HTPASSWD_',
    'JwtAuthOptions': 'VERTX_AUTH_JWT_', 'LdapAuthOptions': 'VERTX_AUTH_LDAP_',
    'OAuth2JwtOptions': 'VERTX_AUTH_OAUTH2_JWT_', 'OAuth2Options': 'VERTX_AUTH_OAUTH2_',
    'OtpAuthOptions': 'VERTX_AUTH_OTP_', 'PropertyFileAuthOptions': 'VERTX_AUTH_PROPERTIES_',
    'GrpcOptions': 'GRPC_', 'RedisOptions': 'REDIS_', 'AddressResolverOptions': 'VERTX_ADDR_RESOLVER_',
    'EventBusOptions': 'VERTX_EVENTBUS_', 'FileSystemOptions': 'VERTX_FILESYSTEM_',
    'Verticle': 'VERTX_VERTICLE_', 'VertX': 'VERTX_', 'VertxEventOptions': 'VERTX_EVENT_',
    'HttpServerOptions': 'HTTP_', 'GraphiteOptions': 'METRICS_GRAPHITE_',
    'PrometheusOptions': 'METRICS_PROMETHEUS_', 'Telemetry': 'TELEMETRY_', 'TelemetryOptions': 'TELEMETRY_'
}
ALIASES = {
    'OAuth2Options.scopeDelimiter': 'SCOPE_SEPARATOR', 'OAuth2Options.userInfoPath': 'USERINFO_PATH',
    'GrpcOptions.scheduleDeadlineAutomatically': 'SCHEDULE_DEADLINE', 'GrpcOptions.grpcWebEnabled': 'WEB_ENABLED',
    'RedisOptions.maxWaitingHandlers': 'MAX_WAITING', 'AddressResolverOptions.cacheMinTimeToLive': 'CACHE_MIN_TTL',
    'AddressResolverOptions.cacheMaxTimeToLive': 'CACHE_MAX_TTL', 'AddressResolverOptions.cacheNegativeTimeToLive': 'CACHE_NEGATIVE_TTL',
    'AddressResolverOptions.roundRobinInetAddress': 'ROUND_ROBIN', 'FileSystemOptions.classPathResolvingEnabled': 'CLASSPATH_RESOLVING',
    'FileSystemOptions.fileCachingEnabled': 'FILE_CACHING', 'Verticle.value': 'WORKER_POOL_NAME',
    'Telemetry.useInMemoryExporters': 'USE_IN_MEMORY', 'TelemetryOptions.useInMemoryExporters': 'USE_IN_MEMORY',
    'HttpServerOptions.httpEnabled': '!HTTP_ENABLED', 'HttpServerOptions.httpsEnabled': '!HTTPS_ENABLED',
    'HttpServerOptions.httpsPort': '!HTTPS_PORT', 'HttpServerOptions.http2Enabled': '!HTTP2_ENABLED',
    'HttpServerOptions.http2ClearTextEnabled': '!HTTP2_CLEAR_TEXT_ENABLED', 'HttpServerOptions.maxBodySize': '!VERTX_MAX_BODY_SIZE',
    'VertxEventDefinition.value': '!VERTX_EVENT_ADDRESS_<ADDR>',
}
ANNOTATION_ONLY = {
    'AbacOptions.policies', 'AuthOptions.keyStore', 'AuthOptions.pubSecKeys',
    'JwtAuthOptions.pubSecKeys', 'JwtAuthOptions.jwks', 'JwtPubSecKey.algorithm', 'JwtPubSecKey.buffer', 'JwtPubSecKey.path', 'JwtPubSecKey.id',
    'GrpcOptions.defaultConnection', 'GrpcOptionsContainer.value', 'RedisOptions.endpoints', 'RedisOptions.defaultConnection', 'RedisOptionsContainer.value',
    'Verticle.capabilities', 'VertxEventDefinition.options', 'Match.value', 'Match.type', 'Match.alias',
    'MetricMethod.name', 'MetricMethod.tags', 'MetricsOptions.monitoredEventBusHandlers', 'MetricsOptions.monitoredHttpServerUris',
    'MetricsOptions.monitoredHttpServerRoutes', 'MetricsOptions.monitoredHttpClientEndpoints', 'MetricsOptions.graphite', 'MetricsOptions.prometheus',
    'OAuth2Options.jwtOptions', 'SpanAttribute.value', 'Trace.value'
}
RESERVED = {'orderedByHeader', 'resumeAtMessages', 'batchWindowMs', 'batchMax'}
GROUPS = {
    'HttpServerOptions': 'HTTP Server', 'VertX': 'Vert.x Runtime & Consumers', 'VertxEventDefinition': 'Vert.x Runtime & Consumers',
    'VertxEventOptions': 'Vert.x Runtime & Consumers', 'Verticle': 'Verticle Deployment', 'EventBusOptions': 'EventBus',
    'FileSystemOptions': 'FileSystem', 'AddressResolverOptions': 'Address Resolver', 'GrpcOptions': 'gRPC', 'GrpcOptionsContainer': 'gRPC',
    'RedisOptions': 'Redis', 'RedisOptionsContainer': 'Redis'
}
SPECIAL = {
    'vertx.MetricsOptions.enabled': 'Base Vert.x metrics flag. When the GuicedEE metrics module is installed, its configurator supplies metrics options; use METRICS_ENABLED to control that integration.',
    'VertX.eventLoopPoolSize': 'Annotation -1 inherits processor-based sizing; a configured pool size must be positive.',
    'VertX.haEnabled': 'Requires an enabled cluster manager; does not enable clustering itself.',
    'AddressResolverOptions.hostsRefreshPeriod': 'GuicedEE defaults this pair to milliseconds; upstream raw default unit is nanoseconds. Zero disables refresh.',
    'AddressResolverOptions.ndots': '-1 in the annotation inherits the system resolver setting.',
    'EventBusOptions.clusterPublicPort': '-1 inherits the cluster bind port; the setter is only called for an explicit port.',
    'FileSystemOptions.fileCacheDir': 'Empty inherits the upstream cache directory (including Vert.x JVM property overrides).',
    'PrometheusOptions.port': '-1 shares the application HTTP listener; 0..65535 creates an isolated managed listener. Path applies on that listener.',
    'RedisOptions.database': '-1 preserves the URI database; non-negative values replace it on every endpoint. Redis Cluster supports database 0 only.',
    'OAuth2Options.scopes': 'Comma-separated request defaults, applied to authorization URLs and token credentials only when the request omits scopes.',
    'OAuth2Options.redirectUri': 'Default for authorization URLs and token credentials; explicit request values take precedence.',
    'OAuth2Options.readTimeout': 'HTTP connection idle timeout in milliseconds, not an overall request deadline; 0 keeps upstream defaults.',
    'JwtAuthOptions.leeway': '-1 inherits AuthOptions.leeway / VERTX_AUTH_LEEWAY; provider override or non-negative annotation wins, including zero.',
    'OAuth2JwtOptions.leeway': '-1 inherits AuthOptions.leeway / VERTX_AUTH_LEEWAY; provider override or non-negative annotation wins, including zero.',
    'HttpServerOptions.http2Enabled': 'Selects HTTPS ALPN protocols; false advertises HTTP/1.1 only.',
    'HttpServerOptions.http2ClearTextEnabled': 'Controls cleartext H2C independently of HTTPS ALPN.',
    'Verticle.maxWorkerExecuteTime': 'Used by Vert.x for named worker pools; always applied with maxWorkerExecuteTimeUnit.',
    'Verticle.capabilities': 'Package prefixes select startup hooks; no environment override. Class and package annotations work in scan and SPI modes.',
}
NATIVE = {
    'VertX.eventLoopPoolSize': '2 * available processors', 'VertX.maxEventLoopExecuteTime': '2000000000',
    'VertX.maxEventLoopExecuteTimeUnit': 'NANOSECONDS', 'VertX.maxWorkerExecuteTime': '60000000000',
    'VertX.maxWorkerExecuteTimeUnit': 'NANOSECONDS', 'VertX.warningExceptionTime': '5000000000', 'VertX.warningExceptionTimeUnit': 'NANOSECONDS',
    'HttpServerOptions.host': '0.0.0.0', 'AddressResolverOptions.hostsPath': 'system hosts file',
    'AddressResolverOptions.servers': 'system DNS servers', 'AddressResolverOptions.searchDomains': 'system search domains',
    'FileSystemOptions.fileCacheDir': 'Vert.x cache directory', 'FileSystemOptions.classPathResolvingEnabled': 'Vert.x default (normally true)',
    'FileSystemOptions.fileCachingEnabled': 'Vert.x default (normally true)', 'AddressResolverOptions.ndots': 'system resolver',
    'AddressResolverOptions.rotateServers': 'system resolver', 'EventBusOptions.host': 'cluster manager default', 'EventBusOptions.clusterPublicHost': 'cluster manager default',
    'Telemetry.serviceName': 'GuicedEE-Service', 'TelemetryOptions.serviceName': 'GuicedEE-Service',
}

def snake(value):
    return re.sub(r'([a-z0-9])([A-Z])', r'\1_\2', value).upper()

def unit(name):
    if name.endswith('Unit'): return 'TimeUnit enum'
    if name in {'maxEventLoopExecuteTime','maxWorkerExecuteTime','warningExceptionTime','blockedThreadCheckInterval','hostsRefreshPeriod'}: return 'paired TimeUnit'
    if name in {'queryTimeout','connectTimeout','readTimeout','reconnectInterval','clusterPingInterval','clusterPingReplyInterval','prngSeedInterval','batchWindowMs','timeoutMs'}: return 'milliseconds'
    if name in {'idleTimeout','leeway','expiresInSeconds','cacheMinTimeToLive','cacheMaxTimeToLive','cacheNegativeTimeToLive','period'}: return 'seconds'
    if name in {'maxMessageSize','maxBodySize','maxHeaderSize','maxChunkSize','maxFormAttributeSize','maxInitialLineLength'}: return 'bytes'
    return ''

members, keys = inventory()
rows = []
mapped = set()
for member in members:
    module, ann, name = member['module'], member['annotation'], member['attribute']
    identity = ann + '.' + name
    prefix = PREFIXES.get(ann, '')
    if ann == 'MetricsOptions': prefix = 'VERTX_METRICS_' if module == 'vertx' else 'METRICS_'
    suffix = ALIASES.get(identity, snake(name))
    candidate = suffix[1:] if suffix.startswith('!') else prefix + suffix
    env = [] if identity in ANNOTATION_ONLY else [candidate]
    for key in env:
        if key not in keys: raise SystemExit('Missing executable mapping: ' + module + '.' + identity + ' -> ' + key)
    if ann == 'VertxEventOptions': env.append(candidate + '_<ADDR>')
    mapped.update(env)
    group = GROUPS.get(ann, 'Authentication & Authorization')
    if module == 'metrics': group = 'Metrics'
    elif module == 'telemetry': group = 'Telemetry'
    elif ann == 'MetricsOptions': group = 'Vert.x Runtime & Consumers'
    activation = 'Core runtime; environment-only configuration supported.'
    if group == 'Authentication & Authorization': activation = 'Provider annotation and optional auth dependency required; singleton class/package configuration.'
    elif group in ('Redis', 'gRPC'): activation = 'Annotation defines a connection; register a concrete ' + group.replace('gRPC','Grpc') + 'Module to create resources.'
    elif group == 'HTTP Server': activation = 'Web module; HTTP/HTTPS enable flags control listeners. Body/upload fields configure BodyHandler.'
    elif group == 'Metrics': activation = 'Metrics module; exporter flags control JMX, Graphite and Prometheus. Environment-only configuration supported.'
    elif group == 'Telemetry': activation = 'Telemetry module; TelemetryOptions takes priority over the Telemetry alias; environment-only configuration supported.'
    elif ann.startswith('VertxEvent'): activation = 'An event definition is required; per-address key precedes global key, then this annotation.'
    elif ann == 'Verticle': activation = 'Deployment options apply to annotated and default verticles; named worker settings require a pool name.'
    status = 'implemented' if env else 'annotation-only'
    if ann == 'VertxEventOptions' and name in RESERVED: status = 'reserved metadata'
    behavior = SPECIAL.get(module + '.' + identity, SPECIAL.get(identity, ''))
    if status == 'reserved metadata': behavior = 'Reserved metadata: resolved and exposed, with no built-in ordering, batching or resume behavior.'
    default = member['default']
    without = NATIVE.get(identity, default)
    if group in ('Authentication & Authorization','Redis','gRPC') or ann.startswith('VertxEvent') or ann in ('Match','MetricMethod','SpanAttribute','Trace'):
        without = 'inactive / no declaration'
    elif ann == 'Verticle' and name in ('value','capabilities'): without = 'unnamed global deployment'
    if ann in ('Telemetry','TelemetryOptions') and name == 'enabled': without = 'true'
    consumers = sorted({ref.split(':')[0] for key in env for ref in keys[key]['runtime_sources'].split(' | ')})
    rows.append(dict(group=group, module=module, annotation=ann, attribute=name, type=member['type'], unit=unit(name),
                     annotation_default=default, without_annotation=without, environment=env, status=status, activation=activation,
                     behavior=behavior, declaration=member['source'].split(':')[0], consumers=consumers))

EXTRA = {
    'HTTPS_KEYSTORE': ('HTTP Server', 'Keystore path; format inferred from extension unless HTTPS_KEYSTORE_TYPE is set.', 'empty'),
    'HTTPS_KEYSTORE_TYPE': ('HTTP Server', 'JKS, PKCS12 or PFX; empty infers from file extension.', 'auto'),
    'HTTPS_KEYSTORE_PASSWORD': ('HTTP Server', 'Keystore password; configure externally.', 'empty (PFX), changeit (JKS)'),
    'OTEL_EXPORTER_OTLP_ENDPOINT': ('Telemetry', 'Shared OTLP base URL; overrides TELEMETRY_OTLP_ENDPOINT.', 'TELEMETRY_OTLP_ENDPOINT'),
    'OTEL_EXPORTER_OTLP_TRACES_ENDPOINT': ('Telemetry', 'Complete traces URL; precedes TELEMETRY_TRACES_ENDPOINT, then shared base + /v1/traces.', 'derived from shared base'),
    'OTEL_EXPORTER_OTLP_LOGS_ENDPOINT': ('Telemetry', 'Complete logs URL; precedes TELEMETRY_LOGS_ENDPOINT, then shared base + /v1/logs.', 'derived from shared base'),
    'HOSTNAME': ('Telemetry', 'OpenTelemetry host.name resource attribute.', 'localhost'),
}
for key in sorted(keys.keys() - mapped):
    if key not in EXTRA: raise SystemExit('Unclassified executable key: ' + key)
    group, description, default = EXTRA[key]
    rows.append(dict(group=group, module=keys[key]['module'], annotation='', attribute='', type='String', unit='',
                     annotation_default='none', without_annotation=default, environment=[key], status='environment-only',
                     activation='Module must be present; use the documented source precedence for this key.', behavior=description,
                     declaration='', consumers=sorted({r.split(':')[0] for r in keys[key]['runtime_sources'].split(' | ')})))

contract = dict(vertx_version='5.2.3', precedence=['JVM property','OS environment','nearest .env.local','nearest .env','application defaults','option annotation / runtime default'],
                annotation_text_resolution=dict(primary_key='Check the normal option key across all sources first.',
                    bare_name='If the option is absent, look up the whole annotation text using normal source order; retain the literal when absent.',
                    arrays='Resolve each annotation entry before joining.',
                    placeholders='Explicit placeholders remain supported, including in referenced values.',
                    indirection='One bare-name lookup only; explicit external option values and placeholder results are not bare references.'),
                annotation_members=len(members), environment_keys_and_templates=len(keys), settings=rows)

# The pinned upstream setter inventory also records deliberately programmatic options.
upstream = list(csv.DictReader((SITE / 'docs/reviews/vertx-5.2.3/upstream-option-surface.csv').open(encoding='utf-8')))
setter_aliases = {'setUseDaemonThread':'useDaemonThreads', 'setHAGroup':'haGroup',
                 'setHttp2ClearTextEnabled':'http2ClearTextEnabled', 'setHostsRefreshPeriodUnit':'hostsRefreshPeriodUnit',
                 'setFileCacheDirAsExactPath':'fileCacheDirAsExactPath'}
coverage = []
for item in upstream:
    attribute = setter_aliases.get(item['setter'], item['annotation_attribute'])
    matching = [r for r in rows if r['annotation']==item['annotation'] and r['attribute']==attribute]
    hook = 'VertxConfigurator.options(VertxOptions)'
    if item['annotation']=='HttpServerOptions': hook = 'VertxHttpServerOptionsConfigurator.builder(HttpServerOptions)'
    elif item['annotation']=='Verticle': hook = 'Programmatic Vertx.deployVerticle with DeploymentOptions'
    coverage.append(dict(upstream_api=item['upstream_api'], setter=item['setter'],
                         annotation=('@'+item['annotation']+'.'+attribute if matching else ''),
                         status=('annotation' if matching else 'programmatic'), extension=hook))
contract['upstream_setters'] = coverage

def write(path, value):
    path = SITE / path
    if CHECK:
        if not path.exists() or path.read_text(encoding='utf-8') != value: raise SystemExit('Out of date: ' + str(path))
    else:
        path.parent.mkdir(parents=True, exist_ok=True); path.write_text(value, encoding='utf-8')

write('docs/configuration/vertx-5.2.3.json', json.dumps(contract, indent=2, ensure_ascii=False) + '\n')
coverage_csv = io.StringIO(newline='')
writer = csv.DictWriter(coverage_csv, fieldnames=list(coverage[0]), lineterminator='\n')
writer.writeheader(); writer.writerows(coverage)
write('docs/configuration/vertx-5.2.3-upstream.csv', coverage_csv.getvalue())
java = ['package com.guicedee.website.pages;', '', '/** Generated by tools/vertx_configuration.py; edit the contract mappings there. */',
        'final class VertxConfigurationCatalog {', '    private VertxConfigurationCatalog() {}', '    static String[][] rows(String group) {', '        return switch (group) {']
for group in dict.fromkeys(row['group'] for row in rows):
    java.append('            case ' + json.dumps(group) + ' -> new String[][] {')
    for row in (r for r in rows if r['group'] == group):
        values = [('Status: ' + row['status'] if not row['annotation'] else '@' + row['annotation'] + '.' + row['attribute']),
                  ', '.join(row['environment']) or '(annotation only)', row['type'] + (' / '+row['unit'] if row['unit'] else ''),
                  row['annotation_default'], row['without_annotation'], row['status'] + '. ' + (row['behavior'] or row['activation'])]
        java.append('                {' + ', '.join(json.dumps(v, ensure_ascii=False) for v in values) + '},')
    java.append('            };')
java.append('            case "Programmatic Options" -> new String[][] {')
for item in coverage:
    values = [item['upstream_api']+'.'+item['setter'], item['annotation'] or '(programmatic only)',
              'Vert.x 5.2.3 API', 'upstream default', 'upstream default', item['extension']]
    java.append('                {' + ', '.join(json.dumps(v) for v in values) + '},')
java.append('            };')
java += ['            default -> throw new IllegalArgumentException("Unknown configuration group: " + group);', '        };', '    }', '}', '']
write('src/main/java/com/guicedee/website/pages/VertxConfigurationCatalog.java', '\n'.join(java))
example = ['# GuicedEE configuration for Vert.x 5.2.3.', '# Generated reference: uncomment only the settings your application needs.',
           '# Values below are annotation defaults, not a runnable deployment preset.', '# Precedence: JVM > OS > .env.local > .env > application > option annotation/default.',
           '# Annotation text: normal option key first, then bare-name lookup in the same source order, then literal.',
           '# Array entries resolve separately; explicit placeholders remain supported. Bare names are followed once.', '']
seen=set()
for row in rows:
    for key in row['environment']:
        if key in seen: continue
        seen.add(key)
        example += ['# '+key+' — '+(row['annotation']+'.'+row['attribute'] if row['annotation'] else row['behavior']),
                    '# '+row['status']+'; default: '+row['annotation_default']+'; without annotation: '+row['without_annotation'], '# '+key+'=', '']
write('docs/configuration/vertx-5.2.3.env.example', '\n'.join(example))
print(('Verified' if CHECK else 'Generated') + f' {len(members)} annotation members and {len(keys)} environment keys/templates for Vert.x 5.2.3')
