# Kubernetes & DevOps Toolset Overview

A brief synopsis of the tools defined in `devbox.json` for this course.

## 1. Cluster & Core Kubernetes
- **kind**: "Kubernetes in Docker." Runs local clusters using Docker containers as nodes.
- **kubectl**: The primary CLI for interacting with Kubernetes clusters.
- **k9s**: Terminal UI for easier cluster navigation and management.
- **kubectx**: Utility for quickly switching between different Kubernetes clusters/contexts.
- **kubent**: (Kube No-Trouble) Scans clusters for deprecated APIs.

## 2. Configuration & Packaging
- **helm**: The "package manager" for Kubernetes, using Charts for complex deployments.
- **kustomize**: Template-free customization of K8s manifests (built into kubectl).
- **kluctl**: Handles complex, multi-environment deployments.

## 3. Development Workflow & Automation
- **task (go-task)**: A modern, YAML-based replacement for `make`.
- **tilt**: Automates the "code -> build -> deploy" loop with live updates.
- **ko**: Fast, Docker-less container building and deployment specifically for Go.
- **act**: Runs GitHub Actions workflows locally.

## 4. Cloud & Registry Tools
- **civo**: CLI for Civo Cloud (K3s-focused provider).
- **gcloud**: Google Cloud SDK (customized for GKE authentication).
- **oras**: Tools for using container registries (OCI) for arbitrary artifacts.

## 5. Utilities & Runtimes
- **jq / yq-go**: Power-user tools for processing JSON and YAML data.
- **gum**: Creates interactive and visual shell scripts (pickers, spinners).
- **envsubst**: Replaces environment variables in text files (simple templating).
- **gh**: GitHub CLI for repository and PR management.
- **go / python312 / nodejs_20 / poetry**: Core programming runtimes and package managers.
