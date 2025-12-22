import axios from 'axios';

interface BaseModel {
  id?: number
}

abstract class BaseApiClient<T extends BaseModel> {
  protected constructor(readonly endpointUrl: string) {
  }

  async fetchAll(abortController: AbortController|null): Promise<T[]> {
    const response = await axios.get(
      this.endpointUrl,
      {signal: abortController?.signal}
    );

    await new Promise(resolve => setTimeout(resolve, 1000));

    return response.data
  }

  async save(model: T): Promise<T> {
    if (model.id) {
      const response = await axios.put(`${this.endpointUrl}/${model.id}`, model);
      return response.data;
    } else {
      const response = await axios.post(this.endpointUrl, model);
      return response.data;
    }
  }

  async delete(id: number): Promise<void> {
    void await axios.delete(`${this.endpointUrl}/${id}`);
  }
}

export default BaseApiClient;
export {type BaseModel}