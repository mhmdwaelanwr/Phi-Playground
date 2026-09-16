# Phi Playground

[![Android CI](https://github.com/mhmdwaelanwr/Phi-Playground/actions/workflows/android-ci.yml/badge.svg)](https://github.com/mhmdwaelanwr/Phi-Playground/actions/workflows/android-ci.yml)
[![Repository Health](https://github.com/mhmdwaelanwr/Phi-Playground/actions/workflows/repository-health.yml/badge.svg)](https://github.com/mhmdwaelanwr/Phi-Playground/actions/workflows/repository-health.yml)

Phi Playground is an Android/Kotlin playground for experimenting with LLM-oriented UI concepts using Jetpack Compose.

The current repository focuses on two prototype experiences:

- an interactive tokenization visualizer
- a streamed text-generation UI with simulated output/failure states

## Project Status

**Clean experimental baseline.** The project is intentionally small and is being preserved in a buildable, documented state so deeper Phi/on-device model work can be added later.

Important: the current implementation does **not** bundle or execute a Microsoft Phi model yet. Tokenization is currently a simplified whitespace-based visualization and generation is simulated with local dummy chunks. This repository should therefore be treated as an LLM UX/prototyping playground, not as a completed on-device inference implementation.

## Current Stack

- Kotlin
- Android
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel / Flow-based state
- Gradle Kotlin DSL

Current Android configuration:

- `minSdk`: 28
- `targetSdk`: 36
- `compileSdk`: 36
- Gradle wrapper: 8.13

## Current Features

### Tokenization Visualizer

The tokenization screen visualizes text as colored token-like units. The current tokenizer is a prototype implementation based on whitespace splitting rather than a real Phi tokenizer.

### Interactive Generation

The generation flow demonstrates:

- prompt input
- streamed response chunks
- temperature/max-token controls at the UI/domain boundary
- simulated initial failure
- simulated mid-stream failure

The repository currently uses a local fake generation repository, so no model download, API key, or network backend is required for the prototype behavior.

## Project Structure

```text
app/src/main/java/com/anwar/phiplayground/llm/
├── MainActivity.kt
├── core/
├── data/
├── domain/
├── feature_generation/
├── feature_tokenization/
└── ui/
```

The codebase contains some early/legacy duplicate placeholders alongside the newer feature folders. Cleanup is intentionally staged instead of rewriting the architecture during the baseline pass.

## Build

Requirements:

- JDK 17+
- Android SDK with API 36 available

```bash
./gradlew :app:assembleDebug
```

## Tests

```bash
./gradlew :app:testDebugUnitTest
```

The current repository only contains starter-level test coverage. Expanding tests is part of the roadmap.

## Development Direction

The next meaningful technical milestone is not more UI polish; it is replacing the simulated tokenization/generation layer with a real, documented local model pipeline while keeping the UI/domain boundary clean.

See [`ROADMAP.md`](ROADMAP.md) for the staged plan.

## Security / Model Files

Do not commit API keys, credentials, signing material, private model credentials, or large downloaded model weights directly into the repository. See [`SECURITY.md`](SECURITY.md).

## License

Original Phi Playground project-specific source code and materials are copyright © 2026 Mohamed Anwar. All rights reserved unless explicitly stated otherwise. Third-party dependencies and model artifacts remain governed by their respective licenses and terms.
