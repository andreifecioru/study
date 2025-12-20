import BaseApiClient, {type BaseModel} from "./BaseApiClient.ts";
import axios from "axios";

interface PicModel extends BaseModel {
  description: string,
  alt_description: string,
  thumb_url: string
}

interface PicSchema {
  id: string,
  description: string,
  alt_description: string,
  urls: { [key: string]: string }
}

const ENDPOINT_URL = 'https://api.unsplash.com'
const CLIENT_ID = import.meta.env.VITE_UNSPLASH_CLIENT_ID

class PicsApiClient extends BaseApiClient<PicModel> {
  constructor() {
    super(ENDPOINT_URL);
  }

  async search(searchTerm: string): Promise<PicModel[]> {
    const searchUrl = `${this.endpointUrl}/search/photos`
    // const searchUrl = 'http://localhost:3000/results'
    const response = await axios.get(searchUrl, {
      headers: {
        Authorization: `Client-ID ${CLIENT_ID}`,
      },
      params: {
        query: searchTerm
      }
    });

    await new Promise(resolve => setTimeout(resolve, 2000));

    return response.data.results.map((item: PicSchema) => {
        const {id, description, alt_description, urls} = item;
        return {
          id,
          description,
          alt_description,
          thumb_url: urls.thumb
        }
      }
    );
  }
}

export default PicsApiClient;
export {type PicModel};