import BaseApiClient, {type BaseModel} from "./BaseApiClient.ts";

interface AnimalModel extends BaseModel {
  id: number,
  kind: string,
  image: string,
}

const ENDPOINT_URL = "http://localhost:3000/animals"

class AnimalApiClient extends BaseApiClient<AnimalModel> {
  constructor() {
    super(ENDPOINT_URL);
  }
}

export default AnimalApiClient;

export {type AnimalModel}