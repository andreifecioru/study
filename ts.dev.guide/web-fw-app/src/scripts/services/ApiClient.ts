import axios, { AxiosPromise } from 'axios';
import { BaseProps } from '../models/Model';


class ApiClient<T extends BaseProps> {
  constructor(readonly endpointUrl: string) {}

  fetch(id: number): AxiosPromise<T> {
    return axios.get(`${this.endpointUrl}/${id}`);
  }

  fetchAll(): AxiosPromise<T[]> {
    return axios.get(this.endpointUrl);
  }

  save(props: BaseProps): AxiosPromise {
    const { id } = props;

    if (id) {
      return axios.put(`${this.endpointUrl}/${id}`, props);
    } else {
      return axios.post(this.endpointUrl, props);
    }
  }
}

export { ApiClient };