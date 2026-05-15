# ReplicaSets & Deployments

These controllers manage the lifecycle of Pods, ensuring a desired state (number of copies, image versions) is maintained.

## 1. ReplicaSets (The Foundation)
A ReplicaSet ensures that a specified number of Pod replicas are running at any given time.

### Key Concepts
- **Self-Healing:** If a Pod is deleted or fails, the ReplicaSet automatically creates a new one.
- **Label Selectors:** This is the "glue." The ReplicaSet identifies which Pods to manage based on labels.
- **Template:** The Pod definition used when creating new replicas.

### Minimal Config
```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: nginx-rs
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:latest
```

---

## 2. Deployments (The Standard)
Deployments manage ReplicaSets. They provide declarative updates for Pods and ReplicaSets, enabling zero-downtime rollouts and easy rollbacks.

### Key Concepts
- **Rolling Updates:** Replaces old Pods with new ones gradually.
- **Rollbacks:** Keeps a history of ReplicaSets, allowing you to revert to a previous version (`kubectl rollout undo`).
- **Management:** You almost always use Deployments instead of ReplicaSets directly.

### Minimal Config with Strategy
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deploy
spec:
  replicas: 3
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1          # Max extra pods created during update
      maxUnavailable: 0    # No pods removed until new ones are Ready
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.25.1
```

## Rollout Strategy: `maxSurge` vs `maxUnavailable`
During a `RollingUpdate`, these two fields control the "flow":
- **`maxSurge: 1`**: Temporary capacity. K8s creates 1 extra Pod (Total 4) so it can start the update without killing old ones first.
- **`maxUnavailable: 0`**: Safety first. K8s will NOT delete an old Pod until a new Pod is confirmed `Ready` by its health checks.
