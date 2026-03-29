# VSCode GitHub MCP Server Setup — 2026-03-27

## Problem

VSCode's Problems panel was showing warnings originating from GitHub Copilot Chat's
built-in agent files (`Ask.agent.md`, `Explore.agent.md`, `Plan.agent.md`):

```
Unknown tool 'github/issue_read'
Unknown tool 'github.vscode-pull-request-github/issue_fetch'
Unknown tool 'github.vscode-pull-request-github/activePullRequest'
```

These files live in `~/.config/Code/User/globalStorage/github.copilot-chat/` and
are not part of the project — they're Copilot Chat's global agent definitions.

## Root cause

The agents reference tools from two sources:
- `github.vscode-pull-request-github/*` — provided by the **GitHub Pull Requests** VSCode extension
- `github/issue_read` — provided by the **GitHub MCP Server** binary

Neither was installed/configured.

## Fix

### Step 1 — Install the GitHub Pull Requests extension

Installing `github.vscode-pull-request-github` resolved the `issue_fetch` and
`activePullRequest` warnings.

### Step 2 — Set up the GitHub MCP Server

The `github/issue_read` tool comes from GitHub's official MCP server. It is **not**
on npm — the package `@github/github-mcp-server` does not exist on the npm registry.
It's a Go binary distributed via GitHub Releases.

1. Download the Linux binary from https://github.com/github/github-mcp-server/releases
2. Extract and place at `~/.local/bin/github-mcp-server`
3. Create a GitHub Personal Access Token (fine-grained, with Issues + PRs + Contents read/write)
4. VSCode auto-migrated `"mcp"` config out of `settings.json` into a dedicated
   `~/.config/Code/User/mcp.json` file — use that file, not `settings.json`

`~/.config/Code/User/mcp.json`:
```json
{
    "servers": {
        "github": {
            "command": "/home/andrei/.local/bin/github-mcp-server",
            "args": ["stdio"],
            "env": {
                "GITHUB_PERSONAL_ACCESS_TOKEN": "<your_pat>"
            }
        }
    }
}
```

## Key lessons

- Copilot Chat's agent files are global, not project-scoped — their warnings appear
  in every workspace's Problems panel.
- The GitHub MCP server is a Go binary, not a Node package. The `npx` route does
  not work for this one.
- VSCode enforces that MCP servers go in a dedicated `mcp.json`, not `settings.json`.
  It will warn and offer an auto-fix to migrate the config.
- The `@modelcontextprotocol/server-github` community package is on npm and works,
  but it may not expose the exact same tool names that Copilot Chat's agents expect.
