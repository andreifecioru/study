import requests

API_URL = 'https://datasets-server.huggingface.co'

def hf_query_dataset(dataset_id: str, token: str) -> str:
    headers = {
        'Authorization': f'Bearer {token}'
    }

    url = f'{API_URL}/is-valid?dataset={dataset_id}'

    response = requests.get(url, headers)
    return response.json()

