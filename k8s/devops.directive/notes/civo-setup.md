# Civo CLI Setup Guide

Follow these steps to link your Civo account to this development environment once your account is verified.

## 1. Get your API Key
1. Log in to your [Civo Dashboard](https://dashboard.civo.com).
2. Navigate to **Settings** > **Security** or search for "API Key".
3. Copy your API Key.

## 2. Authenticate the CLI
Run the following command inside your `devbox shell`:
```bash
civo apikey add my-account <YOUR_API_KEY_HERE>
```

## 3. Set as Current
If you have multiple keys, tell Civo which one to use:
```bash
civo apikey current my-account
```

## 4. Verify Connection
Run this to see if the CLI can talk to the Civo API:
```bash
civo region list
```
*If you see a list of regions (NYC1, LON1, etc.), you are successfully connected.*

## 5. (Optional) Check Pricing/Sizes
You can see what node types are available for your $250 credit:
```bash
civo size list
```

## Common Commands
- `civo kubernetes list`: Show all your cloud clusters.
- `civo kubernetes create <name>`: Create a new K3s cluster.
- `civo kubernetes config <name> --save`: Download the kubeconfig and merge it into your local `~/.kube/config`.
- `civo kubernetes remove <name>`: Delete a cluster (to save credits).

---
**Note:** It may take up to 24 hours for Civo to verify new accounts. Once verified, the $250 credit is usually applied automatically for the first 30 days.
