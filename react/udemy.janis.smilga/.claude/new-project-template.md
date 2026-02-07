# New React Project Setup Template

## Standard Configuration

All projects in this course should be created with the following setup:

### Technology Stack
- **Vite** - Build tool and dev server
- **React 18+** - UI library
- **TypeScript** - Strict mode enabled
- **Path Aliases** - For cleaner imports

---

## Setup Steps

### 1. Create Vite Project

```bash
npm create vite@latest <project-name> -- --template react-ts
cd <project-name>
npm install
```

### 2. Install Additional Dependencies

```bash
npm install -D @types/node
```

### 3. Configure TypeScript (tsconfig.app.json)

Add path aliases to `compilerOptions`:

```json
{
  "compilerOptions": {
    // ... existing options ...
    "strict": true,  // ← Already enabled by default

    /* Path Aliases */
    "baseUrl": ".",
    "paths": {
      "@/*": ["./src/*"],
      "@components/*": ["./src/components/*"]
    }
  }
}
```

**Note:** Vite's default template already enables strict mode. Verify it's present.

### 4. Configure Vite (vite.config.ts)

Add path resolution:

```typescript
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@components': path.resolve(__dirname, './src/components'),
    },
  },
})
```

### 5. Create Folder Structure

```bash
mkdir -p src/components
```

---

## Path Alias Usage

Once configured, you can import like this:

```typescript
// Instead of: import Button from '../../components/Button'
import Button from '@components/Button'

// Instead of: import { helper } from '../../utils/helper'
import { helper } from '@/utils/helper'
```

---

## TypeScript Standards

- **Strict mode**: Always enabled, no exceptions
- **No `any`**: Use `unknown` and type guards when needed
- **Explicit types**: Type all props, state, and function returns
- **React types**: Use built-in types (`ReactNode`, `JSX.Element`, `ComponentProps`, etc.)

---

## Verification

After setup, verify everything works:

```bash
npm run dev
```

The dev server should start without TypeScript errors.