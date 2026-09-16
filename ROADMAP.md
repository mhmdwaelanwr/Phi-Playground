# Phi Playground Roadmap

Phi Playground is being handled in stages: preserve a clean experimental baseline first, then return for deeper model/runtime work.

## Phase 0 — Repository Baseline ✅

- cleaned Android source import
- generated/local files excluded
- repository documentation added
- Android CI added
- repository-health checks added
- project status clarified so simulated features are not presented as real Phi inference

## Phase 1 — Stabilize the Existing Prototype

- remove/deprecate duplicate placeholder source files
- consolidate token models/repositories into one feature path
- add deterministic tests for tokenization behavior
- add tests for generation success/failure state transitions
- improve navigation/back behavior
- move visible strings into Android resources where appropriate
- review accessibility and dark/light Compose behavior

## Phase 2 — Real Tokenization

Replace the whitespace-based prototype with a tokenizer that matches the chosen Phi model/runtime.

- choose the exact Phi model family/version
- document tokenizer/model compatibility
- load tokenizer assets without committing restricted/large model files
- show real token IDs and decoded pieces
- add token count and context-window information
- add deterministic tokenizer tests

## Phase 3 — On-device Phi Inference

Evaluate and implement a real Android inference path.

Possible runtime directions should be evaluated from current official model/runtime support rather than assumed up front.

Goals:

- local model loading
- streaming generation
- cancellation
- generation parameters
- memory/performance measurement
- graceful unsupported-device handling
- model download/import UX
- no hardcoded model credentials or URLs requiring private access

## Phase 4 — Playground Tooling

- prompt presets
- generation timing
- tokens/second metrics
- first-token latency
- memory usage notes
- model/runtime information panel
- copy/export output
- session history
- comparison mode for generation parameters

## Phase 5 — Quality

- unit tests for repositories/use cases/view models
- Compose UI tests for core flows
- instrumentation tests on representative Android devices
- lifecycle/interruption testing
- offline model-load tests
- large-context stress testing

## Phase 6 — Polish

- screenshots/GIF demo
- proper launcher branding
- GitHub topics/about metadata
- release notes
- optional APK release workflow once real model/runtime behavior is stable

## Current Rule

Do not describe the app as running Phi locally until a real model runtime is integrated and validated. Until then, the project is an LLM UX and architecture playground preparing for that milestone.
