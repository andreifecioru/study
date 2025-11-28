import { User } from '../models/User';
import { randomInt } from '../utils/random';

class UserForm {

    constructor(readonly parent: Element, readonly model: User) {
        this.model.on('change', () => {
            this.render();
        });
    }

    eventsMap(): { [key: string]: () => void } {
        return {
            'click:button#set-name': this.onButtonSetNameClick.bind(this),
            'click:button#set-age': this.onButtonSetAgeClick.bind(this),
            'mouseenter:h1': this.onHeaderHover.bind(this)
        };
    }

    onHeaderHover(): void {
        console.log('Header hover');
    }

    onButtonSetNameClick(): void {
        const nameInputElement = this.parent.querySelector<HTMLInputElement>('input#input-name');
        const newName = nameInputElement ? nameInputElement.value : '';
        this.model.set({ name: newName });
    }

    onButtonSetAgeClick(): void {
        const newAge = randomInt(10, 80);
        console.log(`Setting age to ${newAge}`);
        this.model.set({age: newAge});
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
            <input id='input-name' />
            <button id='set-name'>Update name</button>
            <hr />
            <div>
                <button id='set-age'>Set random age</button>
            </div>
        </div>`;
    }

    render(): void {
        this.parent.innerHTML = '';

        const template = document.createElement('template');
        template.innerHTML = this.template();
        this.bindEvents(template.content);
        this.parent.appendChild(template.content);
    }
}

export { UserForm };