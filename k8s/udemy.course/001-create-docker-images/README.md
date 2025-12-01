## Chapter: [Build Custom Docker Images via Docker Server] (#)
- Date: 1 Dec 2025
- Lecture count / Duration: 48m

## Status
- [ ] Chapter completed
- [ ] Practical exercises done (if any)
- [ ] Notes / clarifications requested

## Objectives
- how to build a custom image starting from a base image
- understand how images are created
- understand layer caching
- image tagging

## Prerequisites
- we will be working with Docker desktop
- environemnt is Linux (Mint)

## Key Concepts — High-level (Conceptual)
- Concept 1: Short definition + why it matters
- Concept 2: Short definition + typical use cases
- Concept 3: Why it relates to other concepts


## Run / Verify Commands
- Commands you used to build/test or verify the example (these can be short, course-specific later). Example checks:
  - Build image: `docker build -t local/redis:custom .`
  - Run the container: `docker run --rm -d --name redis local/redis:custom`
  - Cleanup: `docker system prune -af`

## Trade-offs / Alternatives
  - Problem or scenario (e.g., stateful vs stateless): trade-offs and recommended choices
  - Alternative tools or patterns and a short note about when to prefer them

## Common Pitfalls / Gotchas (Course-specific)
  - install deps first, then copy files; avoid `COPY` commands early in the image build script (optimizes the layer cache)
  - use `apk add --no-cache` to avoid leaving package index cache in the image (smaller builds)
  - add a `.dockerignore` file to keep the build context minimal (e.g. `node_modules` should be ingnored)

## Troubleshooting Checklist (Quick things to check first)

## Reflection Questions (3–5)
- Question 1: (Conceptual) e.g., why do we prefer immutable container images?
- Question 2: (Application) e.g., how do you debug a failing service in the cluster?
- Question 3: (Trade-off) e.g., when would you use a DaemonSet over a Deployment?

## Practice Projects (Small / Medium / Advanced)
- Small: Quick 1–2 hour exercise — what to build and learning goals
- Medium: Multi-component or local infra; includes tests and verification steps
- Advanced: Production-like constraints (HA, monitoring, CI/CD integration, secrets management)

Example proposals:
- Small: Create a Dockerfile for a static site and run it locally with Kubernetes (single Pod + Service).
- Medium: Build a multi-container application (frontend + backend + db), deploy with a Deployment and Service, and set up a simple Ingress.
- Advanced: Implement CI (GitHub Actions), build/push images to a registry, deploy to a local k8s cluster with Helm, set up Prometheus monitoring and alerting for the application.

## Additional Resources / Links
- Course-recommended docs, blog posts, or official references

## Notes / Personal Takeaways
- Personal notes and TODOs for follow up

## Appendix (Files / Commands)
- List of project files (Dockerfile, deployment.yaml, README.md, etc.)

---

> Usage tips:
> - Fill in the `Run / Verify` with the exact commands the course shows when you get to the lab steps. We will follow exactly when requested.
> - When you finish the chapter, check the "Status" section and draft any clarifying questions to send me.

<!-- End of template -->
