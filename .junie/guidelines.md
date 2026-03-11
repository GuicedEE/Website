# Junie Guidelines - GuicedEE Website

- Follow documentation-first, stage-gated workflow in `RULES.md` and `rules/RULES.md`.
- Junie exception: do not pause for stage approvals; proceed and record that stages are auto-approved.
- Document Modularity Policy: split large docs (300-500+ lines) into topic files, remove monoliths, update all indexes and links, use kebab-case names, and keep each file linked to its topic index.
- Forward-Only Change Policy: no backward compatibility unless explicitly requested; update or remove references in the same change; avoid legacy anchors or duplicate files.
- Behavioral/Technical commitments: clear technical English, keep continuity, declare limits, use Markdown, and follow project naming conventions (GuicedEE, Angular 20, WebAwesome, Log4j2 with Lombok @Log4j2, CRTP fluent APIs).
- Close loops: PACT <-> GLOSSARY <-> RULES <-> GUIDES <-> IMPLEMENTATION with links; use `docs/PROMPT_REFERENCE.md` for stack and diagram references.
