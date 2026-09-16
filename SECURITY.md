# Security Policy

Phi Playground is an experimental Android project. The current baseline does not require production credentials or a remote backend.

## Repository Rules

Do not commit:

- API keys, access tokens, passwords, or private endpoints
- Android signing keys or certificates
- `.env` or local configuration files containing secrets
- private model registry/download credentials
- proprietary or restricted model weights
- user prompts or generated content containing personal/sensitive data

Large model files should normally be downloaded or imported outside Git history and covered by the applicable model license/terms.

## Reporting a Security Issue

Avoid opening a public issue for vulnerabilities that could expose credentials, private data, or restricted assets. Contact the repository owner privately with reproduction details.

## Production Boundary

This repository is a playground, not a hardened production inference application. Any future real model integration should review model provenance, storage, integrity verification, permissions, logging, memory handling, network behavior, and update/download security before release.
