"""
Image utility functions for HuggingFace experiments
"""
from PIL import Image
from typing import Tuple


def scale_image(image: Image.Image, factor: float) -> Image.Image:
    """
    Scale an image by a given factor while maintaining aspect ratio.
    
    Args:
        image: PIL Image object
        factor: Scale factor (e.g., 0.5 for 50%, 2.0 for 200%)
    
    Returns:
        Scaled PIL Image object
    """
    new_width = int(image.width * factor)
    new_height = int(image.height * factor)
    return image.resize((new_width, new_height))
