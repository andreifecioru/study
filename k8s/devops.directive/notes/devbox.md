# Devbox: Reproducible Development Environments

## Why Devbox?
Devbox solves the "it works on my machine" problem by providing isolated, reproducible shells using the **Nix** package manager, without requiring you to learn the complex Nix language. For this K8s course, it ensures you use the exact tool versions (kubectl, kind, helm, etc.) required by the instructor.

## How It Works
- **Configuration:** Uses a `devbox.json` file to define required packages.
- **Isolation:** Instead of containers, it manipulates your shell's `PATH`. It prepends Nix-managed binary paths, ensuring they take priority over globally installed tools.
- **Nix Integration:** Leverages the Nix store for package storage, keeping your global system clean.
- **Flakes:** Supports **Nix Flakes** for custom requirements, like bundling the `gke-gcloud-auth-plugin` with the Google Cloud SDK.

## Setup & Bootstrapping

### 1. Installation
Install the Devbox CLI:
```bash
curl -fsSL https://get.jetpack.io/devbox | bash
```

### 2. Initialization
- If `devbox.json` exists, run:
  ```bash
  devbox update
  ```
- This modernizes the config and generates a `devbox.lock` file to pin versions.

### 3. Custom Packages (Flakes)
For specialized tools (e.g., GKE auth plugin), create a `flake.nix` in a subdirectory (like `./gcloud`) and reference it in `devbox.json`:
```json
"packages": [ "path:gcloud#google-cloud-sdk" ]
```

### 4. Entering the Environment
Run the following to activate the environment:
```bash
devbox shell
```
*Note: This drops you into a sub-shell (e.g., zsh) with your customizations (Oh-My-Zsh) largely intact, but with the project's tools available.*

## Common Commands
- `devbox shell`: Start the interactive dev environment.
- `devbox run <command>`: Run a single command without entering the shell.
- `devbox update`: Update packages and the lockfile.
- `exit`: Leave the Devbox shell.
