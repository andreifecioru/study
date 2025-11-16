import { EventBus, EventCallback } from "../services/EventBus";
import { ApiClient } from "../services/ApiClient";

interface BaseProps {
  id?: number;
}

abstract class BaseModel<T extends BaseProps> {
  protected state: ModelState<T>;
  protected eventBus: EventBus;
  protected apiClient: ApiClient<T>;

  constructor(props: T, endpointUrl: string) {
    this.state = new ModelState(props);
    this.eventBus = new EventBus();
    this.apiClient = new ApiClient(endpointUrl);
  }

  // state management
  get get() {
    return this.state.get;
  }

  set(updateProps: T): void {
    this.state.set(updateProps);
    this.eventBus.trigger('change');
  }

  get props(): BaseProps {
    return this.state.props;
  }

  get isPersisted(): boolean { 
    return !!this.state.id;
  }

  // eventing
  get on() {
    // we need to keep the 'this' context
    return this.eventBus.on;
  }

  get off() {
    return this.eventBus.off;
  }

  get trigger() {
    return this.eventBus.trigger;
  }

  // back-end CRUD ops
  fetch(): void {
    if (this.isPersisted) {
      this.apiClient.fetch(this.state.id as number)
        .then(
          (response) => {
            this.set(response.data);
          },
          (error) => { 
            console.error(`Failed to fetch user with id ${this.state.id}: ${error}`); 
          }
        )
    } else {
      throw new Error('Cannot fetch an un-persisted model.')
    }
  }

  save(): void {
    this.apiClient.save(this.state.props)
      .then(
        (response) => {
          this.set(response.data);
        },
        (error) => {
          console.error(`Failed to update user with id ${this.state.id}: ${error}`); 
        }
      )
  }

  // misc
  print(): void {
    console.log(this.props);
  }
}

class ModelState<T extends BaseProps> {
  static empty<M extends BaseProps>() {
    return new ModelState({} as M);
  }

  constructor(private state: T) {}

  get = <K extends keyof T>(key: K): T[K] => {
    return this.state[key];
  }

  set(props: T): void {
    Object.assign(this.state, props);
  }

  get props(): T {
    return {...this.state};
  }

  get id(): (number|undefined) {
    return this.state.id;
  }
}

export { BaseModel, ModelState, BaseProps}