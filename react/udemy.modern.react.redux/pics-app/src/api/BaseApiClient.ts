import axios from "axios";

type BaseModel = {
  id?: number | string
}

class BaseApiClient<T extends BaseModel> {

  constructor(readonly endpointUrl: string) {
  }

  async fetchAll(): Promise<T[]> {
    console.log(`Fetching data from remote server: ${this.endpointUrl}`)

    // Introduce a small delay
    await new Promise(resolve => setTimeout(resolve, 2000));

    const response = await axios.get(this.endpointUrl);
    return response.data;
  }
}

export default BaseApiClient;
export {type BaseModel};
