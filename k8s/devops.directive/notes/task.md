# Task: A Modern Task Runner

`task` (provided by the `go-task` package) is a modern replacement for `make`. It uses a simple YAML-based configuration to define build steps, automation scripts, and project workflows.

## Why Task?
- **Human Readable:** Uses YAML instead of the often cryptic Tab-based syntax of Makefiles.
- **Cross-Platform:** Written in Go, ensuring consistent behavior across Linux, macOS, and Windows.
- **Dependency Tracking:** Easily define which tasks must run before others.
- **Fast:** Only runs tasks when necessary by tracking file changes (checksums).

## Basic Taskfile Example
Create a file named `Taskfile.yml` in your project root:

```yaml
version: '3'

vars:
  GREETING: Hello

tasks:
  build:
    desc: Build the application
    cmds:
      - echo "Building..."
      - go build -o bin/app main.go
    sources:
      - ./*.go
    generates:
      - bin/app

  test:
    desc: Run unit tests
    cmds:
      - echo "{{.GREETING}}, running tests..."
      - go test ./...

  default:
    cmds:
      - task: test
      - task: build
```

## Common CLI Commands
- `task --list` (or `task -l`): List all available tasks with their descriptions.
- `task <task-name>`: Run a specific task.
- `task`: Run the `default` task.
- `task --watch <task-name>`: Watch for file changes and re-run the task automatically.
- `task --summary <task-name>`: Show what a task does without running it.

## Reference
For the complete guide and advanced features (like internal tasks, looping, and platforms), visit the official documentation:
[https://taskfile.dev/docs/guide](https://taskfile.dev/docs/guide)
