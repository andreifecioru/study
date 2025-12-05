from setuptools import setup, find_packages

# Read requirements
with open('requirements.txt') as f:
    requirements = [line.strip() for line in f if line.strip() and not line.startswith('#')]

setup(
    name="hf-experiments",
    version="0.1.0",
    description="HuggingFace transformers experiments and learning",
    packages=find_packages(),
    python_requires=">=3.11",
    install_requires=requirements
)