import axios, { AxiosPromise } from 'axios';
import { BaseProps } from '../models/Model';


class ApiClient<T extends BaseProps> {
  constructor(readonly endpointUrl: string) {}

  fetch(id: number): AxiosPromise {
    return axios.get(`${this.endpointUrl}/${id}`)
  }

  save(props: BaseProps): AxiosPromise {
    const { id } = props;

    if (id) {
      return axios.put(this.endpointUrl, props)
    } else {
      return axios.post(this.endpointUrl, props)
    }
  }
}

export { ApiClient };