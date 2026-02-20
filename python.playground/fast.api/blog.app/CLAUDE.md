# Blog App — FastAPI Learning Project

## Purpose

This project is a hands-on learning exercise following the YouTube course:
**[FastAPI Course for Beginners](https://www.youtube.com/watch?v=7AMjmCTumuo&list=PL-osiE80TeTsak-c-QsVeg0YYG_0TeyXI)**

The goal is to understand FastAPI concepts deeply, not just make things work.

## Project Context

- **Stack**: Python 3.14, FastAPI
- **Domain**: A blog application (CRUD for posts, users, etc.)
- **Stage**: Early — skeleton only, building up alongside the course

## Learning Goals

- Understand how FastAPI handles routing, request/response models, and validation
- Learn Pydantic model design and why it matters for APIs
- Understand dependency injection in FastAPI
- Work with a database (likely SQLAlchemy or similar) through FastAPI patterns
- Explore authentication patterns (JWT, OAuth2)

## Claude's Role Here

Act as a **thinking and learning partner**, not a code generator.

- Explain *why* FastAPI makes certain design decisions
- Help reason through Pydantic models before writing them
- Surface tradeoffs (e.g. sync vs async, ORM vs raw SQL)
- Point to FastAPI docs via Context7 when relevant
- Challenge assumptions about API design

Default behavior: **describe and discuss, don't write code** unless explicitly asked.

## Course Progress

Track what has been covered as the project advances. Update this section as you go.

- [ ] Project setup and first route
- [ ] Path and query parameters
- [ ] Request body with Pydantic
- [ ] Response models
- [ ] Database integration
- [ ] CRUD operations
- [ ] Authentication & authorization
- [ ] Deployment

## Notes Convention

Interesting recipes, patterns, and "aha moments" are captured as individual markdown files in the `notes/` folder.

- One concept per file
- Filename: descriptive kebab-case (e.g. `python-find-via-next-generator.md`)
- Structure: title, mental model explanation, short code example
- Invoke `/add-note` to trigger note creation
