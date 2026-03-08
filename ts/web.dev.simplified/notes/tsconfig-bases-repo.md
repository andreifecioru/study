# tsconfig/bases — Curated tsconfig Base Configurations

The `tsconfig/bases` repo (https://github.com/tsconfig/bases) provides officially maintained, environment-specific `tsconfig.json` starting points. Rather than guessing which compiler options are correct for your runtime (Node 20, Vite, Deno, etc.), you extend a base that encodes community best-practice defaults for that target. It keeps your own `tsconfig.json` minimal and focused on project-specific overrides — and it's maintained by the TypeScript team, so it tracks breaking changes in target environments.

```jsonc
// Install: npm install --save-dev @tsconfig/node20
// tsconfig.json
{
  "extends": "@tsconfig/node20/tsconfig.json",
  "compilerOptions": {
    "outDir": "./dist"
    // only project-specific overrides go here
  }
}
```

**Caveats:**
- Bases are opinionated — check what they set before blindly extending, especially `strict`, `module`, and `moduleResolution`.
- Vite scaffolds its own `tsconfig.json` that already reflects browser/bundler conventions; the bases repo is most useful for Node projects or when rolling your own config from scratch.
