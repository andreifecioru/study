# Chapter Template — Fill this in after studying each chapter

> Use this template to summarize concepts, list practical artifacts, and propose practice projects. Keep it concise but include enough detail so you can reproduce the examples later.

---

## Chapter: [Title] (#)
- Date:
- Lecture count / Duration:
- Course (Udemy) video timestamps: [start - end]

## Status
- [ ] Chapter completed
- [ ] Practical exercises done (if any)
- [ ] Notes / clarifications requested

## Objectives
- Short list (3–6 bullets) of what the chapter aims to teach

## Prerequisites
- Things assumed known / required tools or versions (e.g., Docker, kubectl, kind, minikube)

## Key Concepts — High-level (Conceptual)
- Concept 1: Short definition + why it matters
- Concept 2: Short definition + typical use cases
- Concept 3: Why it relates to other concepts

## Diagrams / Mental Models
- Sketch or short ASCII/description of relevant diagrams (e.g., layers in a Docker image, control plane vs data plane in k8s)

## Practical Examples & Artifacts (Minimal reproducible examples)
- File names and short descriptions (Dockerfile, deployment.yaml, service.yaml, etc.)
- Commands to build/run/test (put placeholder commands, we will fill these with exact course steps when we reach them)

Example snippet:
```yaml
# Example manifest
apiVersion: v1
kind: Pod
metadata:
  name: example-pod
spec:
  containers:
  - name: app
    image: <image>
```

## Run / Verify Commands
- Commands you used to build/test or verify the example (these can be short, course-specific later). Example checks:
  - Build image: `docker build -t <name> .`
  - Run pod: `kubectl apply -f <file>`
  - Check resources: `kubectl get pods -o wide`

## Trade-offs / Alternatives
- Problem or scenario (e.g., stateful vs stateless): trade-offs and recommended choices
- Alternative tools or patterns and a short note about when to prefer them

## Common Pitfalls / Gotchas (Course-specific)
- Pitfall 1: Cause + how to detect + how to fix
- Pitfall 2: Cause + how to detect + how to fix

## Troubleshooting Checklist (Quick things to check first)
- Local environment:
  - Docker/Kubernetes version
  - Correct context: `kubectl config current-context`
  - Node status: `kubectl get nodes`
- Manifest/Build:
  - YAML validation: `kubectl apply --dry-run=client -f <file>`
  - Image available locally / accessible by cluster (private registry credentials)
- Logs and runtime issues:
  - Container logs: `kubectl logs <pod> -c <container>`
  - Describe resource: `kubectl describe pod <pod>`
- Networking:
  - Service endpoints: `kubectl get endpoints <svc>`
  - Port forward to a pod: `kubectl port-forward pod/<pod> <localPort>:<podPort>`

> When contacting me for help, please paste: the short description, the commands you ran, the full error/output, and any files you changed.

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
