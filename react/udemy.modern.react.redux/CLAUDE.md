# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a learning repository for the Udemy Modern React & Redux course. It contains multiple independent React + TypeScript + Vite applications built progressively through the course. The user is converting the course's JavaScript to **strict TypeScript**.

## Development Commands

Each app is independent. Run commands from within the specific app directory (e.g., `cd translator-app`):

```bash
npm run dev      # Start Vite dev server with HMR
npm run build    # TypeScript check + Vite production build
npm run lint     # Run ESLint
npm run preview  # Preview production build
```

## Architecture

**Monorepo of independent apps** - no shared root package.json. Each app has its own dependencies and config.

### Apps (in learning progression order)
- `translator-app` - Basic React, CSS modules
- `animals-app` - API integration with axios, Sass styling
- `books-app` - CRUD operations, FontAwesome, uuid
- `pics-app` - Image search/gallery, axios
- `comps-app` - Component library, Tailwind CSS, Context API
- `pda-app` - Bulma CSS framework

### Common Patterns
- `/components` - Reusable components (often with co-located styles)
- `/pages` - Page-level components
- `/contexts` - React Context providers (where applicable)
- `/api` - API client classes (where applicable)

## Tech Stack

- React 19 + TypeScript 5.9 (strict mode)
- Vite 7.2 for builds
- ESLint (flat config) + Prettier
- No testing framework configured

## Teaching Assistant Role

This is a learning project. The user's goal is deep understanding, not velocity. When assisting:

1. **Default to explanation over code generation** - describe approaches conceptually
2. **Point out blind spots** - concepts the course may skim over
3. **Keep explanations focused** - brief first, expand if asked (70+ hours of course content)
4. **Help with TypeScript** - convert JS patterns to strict TS
5. **Stay on curriculum** - avoid derailing into tangential topics
6. **Code snippets allowed** for boilerplate, small illustrations (<10 lines), or when explicitly requested
