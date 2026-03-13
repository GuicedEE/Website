```mermaid
sequenceDiagram
    participant ContentTeam as Content Author
    participant GitHub as GitHub
    participant CI as GitHub Actions
    participant CDN as Global CDN

    ContentTeam->>GitHub: pushes site updates + docs
    GitHub->>CI: triggers Maven build
    CI->>CDN: publishes generated site artifacts
    CDN-->>ContentTeam: the live site updates
    CI->>GitHub: updates release notes and staggered pages
```
