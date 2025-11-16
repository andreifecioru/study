type EventCallback = () => void

class EventBus {
  private eventHandlers: Map<string, EventCallback[]> = new Map();
  
  // alternative declaration
  // private eventHandlers: { [ key: string]: EventCallback[] } = {};

  on = (eventName: string, callback: EventCallback): void => {
    const callbacks = this.eventHandlers.get(eventName) ?? [];
    callbacks.push(callback);
    this.eventHandlers.set(eventName, callbacks);
  }

  off = (eventName: string, callback: EventCallback): void => {
    const callbacks = this.eventHandlers.get(eventName);

    if (callbacks !== undefined) {
      const index = callbacks.findIndex(callback);
      if (index > -1) {
        callbacks.splice(index, 1);
      }

      if (callbacks.length === 0) {
        this.eventHandlers.delete(eventName);
      }
    }
  }

  trigger = (eventName: string): void => {
    const callbacks = this.eventHandlers.get(eventName) ?? [];
    callbacks.forEach((callback) => 
      callback()
    );
  }
}

export { EventBus, EventCallback }