from transformers import pipeline
from utils import scale_image
from PIL import Image, ImageOps
import matplotlib.pyplot as plt
import numpy as np
import sys
from pathlib import Path

import gradio as gr

# resolve data path relative to this script file
IMG_PATH = (Path(__file__).resolve().parent.parent / 'data' / 'image-002.jpg')
if not IMG_PATH.exists():
    print(f"Image not found: {IMG_PATH}", file=sys.stderr)
    raise FileNotFoundError(f"Image not found: {IMG_PATH}")

image = Image.open(IMG_PATH)
 
img_segmenter = pipeline(
    'image-segmentation',
    model='nvidia/segformer-b0-finetuned-ade-512-512'
)

def extract_object(in_img, target_label):
    _in_img = Image.fromarray(in_img)
    base_image = _in_img.copy()

    results = img_segmenter(_in_img)
    for result in results:
        label = result['label']
        if label == target_label:
            mask_image = result['mask']

            # invert the mask
            mask_image = ImageOps.invert(mask_image)

            # apply the mask over the original image
            base_image.paste(mask_image, mask=mask_image)

            return base_image

def main():
    # extrected_object.show()

    image_input = gr.Image(label='Input image')
    target_label = gr.Textbox(label='Label to look for', placeholder='label')

    image_output = gr.Image(label='Image w/ mask applied')

    gr.Interface(
        extract_object, 
        [image_input, target_label], 
        image_output
    ).launch()

    extrected_object = extract_object('person')


if __name__ == '__main__':
    main()