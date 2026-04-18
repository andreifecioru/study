# OpenCode Instructions for py.for.data.analysis

## Workspace Setup
- **Format:** Primarily Jupyter Notebooks (`.ipynb`).
- **Dependencies:** Managed via `pyproject.toml` and `uv`. Core libraries: `numpy`, `pandas`, `matplotlib`, `ipykernel`, `ipympl`.
- **Tooling:** Uses `ruff` for linting and `mypy` for static type checking.
- **Data Location:** All datasets are stored in the `/data` directory. Use relative paths like `data/filename.csv` within notebooks.

## Verification
- **Notebook Testing:** Use `pytest --nbmake` if validation is requested, or execute specific cells using a headless runner if available.
- **Linting & Types:** Use `ruff check .` and `mypy .`.
- **Python Version:** Uses Python 3.13 (stable). `pyproject.toml` specifies `>=3.12`.

## Conventions
- **Imports:** Standard aliases used: `import numpy as np`, `import pandas as pd`.
- **Git:** `.ipynb_checkpoints/` and the `data/` directory are excluded from version control.
- **Scope:** This is a study/course repository. Prioritize clarity and educational value in explanations.
- **Reference Material:** Conceptual deep-dives (e.g., Python protocols) are captured in the `/notes` directory for long-term study reference.
