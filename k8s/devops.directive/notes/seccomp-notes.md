# Seccomp (Secure Computing) in Kubernetes

**Seccomp** acts as a syscall "bouncer" between the container process and the Linux Kernel.

### Mental Model
Applications only need a small subset of the ~300+ available Linux system calls (syscalls). Seccomp limits the container to only the necessary ones, reducing the attack surface.

### `type: RuntimeDefault`
- **What it is:** A pre-defined whitelist of syscalls provided by the container runtime (e.g., `containerd`).
- **Function:** Blocks dangerous or unnecessary syscalls like `mount()`, `reboot()`, or `ptrace()`.
- **Placement:** Usually set at `spec.securityContext` (Pod level) to protect all containers in the Pod.

### Why use it?
- **Defense in Depth:** If an attacker compromises the application (e.g., Nginx), Seccomp prevents them from using kernel-level exploits to "break out" onto the host machine.
- **Enforcement:** If a process attempts a blocked syscall, the Kernel immediately terminates it with a `SIGSYS` error.
