# 🎯 AI Prompt Reference

This document provides a guide for effectively prompting AI agents when working on the GuicedEE project. It links the technology stack, architectural rules, and specialized AI skills available in this repository.

## 🧠 Using AI Skills

This repository contains a set of **AI Skills** located in the [`skills/`](./../skills/) directory. These skills are optimized to provide the agent with precise domain knowledge and constraints.

**Registry of all skills:** [`SKILLS.md`](./../SKILLS.md)

### 🚀 Key Skills Quick-Invoke
- **`$guicedee-inject`**: For bootstrapping, DI, and lifecycle hooks.
- **`$guicedee-vertx`**: For reactive messaging and event-bus consumers.
- **`$guicedee-web`**: For HTTP/HTTPS server and router configuration.
- **`$jwebmp-core`**: For typed HTML/CSS/JS components in Java.
- **`$jwebmp-angular`**: For Angular 20 TypeScript generation and hosting.

## 🏗️ Architecture & Rules

Before generating code, ensure the agent has read the following documents to maintain alignment with project standards:

- [**PACT.md**](./../PACT.md): Core platform agreements.
- [**RULES.md**](./../RULES.md): Formatting, dependency, and coding standards.
- [**GUIDES.md**](./../GUIDES.md): Developer workflows and component tutorials.
- [**Architecture Diagrams**](./../docs/architecture/README.md): C4 and sequence diagrams (rendered via Mermaid MCP).

## 📊 Diagram Rendering

When asking an agent to generate or update diagrams:
1. Use **Mermaid.js** syntax.
2. Ensure the [Mermaid MCP](https://mcp.mermaidchart.com/mcp) is available to render high-fidelity diagrams directly in the conversation.

## 🧩 Stack Selections

Always remind the agent of the following stack constraints:
- **Java 25+** (using latest preview features where applicable).
- **Maven** as the build tool.
- **Guice** for Dependency Injection (via GuicedEE).
- **Vert.x 5** for the reactive engine.
- **JWebMP** for the front-end component model.
- **WebAwesome** for UI components and icons.
