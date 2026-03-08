---
name: add-note
description: Create a reference note capturing a TypeScript concept, pattern, or insight. Use proactively when a concept worth preserving comes up in conversation — e.g. a non-obvious behavior, a useful mental model, or a tradeoff that took reasoning to surface.
argument-hint: concept to capture
user-invocable: true
disable-model-invocation: false
---

Create a note capturing the concept or recipe described by the user.

Save it as a markdown file in the `notes/` folder at the project root.

Follow these rules:
- One concept per file
- Filename: descriptive kebab-case (e.g. `python-find-via-next-generator.md`)
- Title: clear, specific name for the concept
- Body structure:
  1. One-paragraph mental model explanation — the "why", not just the "what"
  2. A short code example (under 15 lines)
  3. Any meaningful tradeoffs or caveats worth remembering

Keep it concise. This is a reference card, not a tutorial.