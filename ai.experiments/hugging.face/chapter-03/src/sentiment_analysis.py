import os
import torch
import transformers
import sys
from transformers import AutoTokenizer, AutoModelForSequenceClassification


def main() -> None:
  input_text = 'I loved the movie, it was fantastic!'
  model_name = 'textattack/distilbert-base-uncased-SST-2'

  tokenizer = AutoTokenizer.from_pretrained(model_name)
  model = AutoModelForSequenceClassification.from_pretrained(model_name)
  # model = model.to('cpu')
  print('Model loaded...')

  tokens = tokenizer(input_text, return_tensors='pt')
  # tokens = { k: v.to('cpu') for k,v in tokens.items() }
  # print(tokens)
  
  print('Running model...')
  with torch.no_grad():
    model_outputs = model(**tokens)
  print('Model execution complete...')
  
  print(model_outputs)

def print_versions():
  print(f'Python: {sys.version}')
  print(f'Torch version: {torch.__version__}')
  print(f'Transformers version: {transformers.__version__}')
  
  
if __name__ == '__main__':
  main()