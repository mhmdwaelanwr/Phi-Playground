# Contributing

Keep `main` as the clean experimental baseline. Use focused branches/PRs for deeper work.

Before opening a PR:

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```

Please avoid committing generated build output, IDE metadata, credentials, signing material, or large/restricted model files.

When changing model behavior, clearly distinguish simulated behavior from real inference and document the exact runtime/model/tokenizer being used.
