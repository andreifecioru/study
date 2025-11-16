import { User } from '../models/User';
class UserForm {

    constructor(readonly parent: Element, readonly model: User) { }

    eventsMap(): { [key: string]: () => void } {
        return {
            'click:button': this.onButtonClick.bind(this),
            'mouseenter:h1': this.onHeaderHover.bind(this)
        };
    }

    onHeaderHover(): void {
        console.log('Header hover');
    }

    onButtonClick(): void {
        console.log('Button clicked');
    }

    bindEvents(element: DocumentFragment): void {
        const eventsMap = this.eventsMap();

        for (let eventKey in eventsMap) {
            const [eventName, selector] = eventKey.split(':');
            const callback = eventsMap[eventKey];

            element.querySelectorAll(selector).forEach(element => {
                element.addEventListener(eventName, callback);
            });
        }
    }

    template(): string {
        return `<div>
            <h1>User Form</h1>
            <p>User ID: ${this.model.get('id')}</p>
            <p>User name: ${this.model.get('name')}</p>
            <p>User age: ${this.model.get('age')}</p>
            <input/ >
            <button>Click me</button>
        </div>`;
    }

    render(): void {
        const template = document.createElement('template');
        template.innerHTML = this.template();
        this.bindEvents(template.content);
        this.parent.appendChild(template.content);
    }
}

export { UserForm };