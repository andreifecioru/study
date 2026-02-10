import { randomInt } from '../utils/random';
import { UserProps } from '../models/User';
import { View } from './View';


class UserForm extends View<UserProps> {
    override eventsMap(): { [key: string]: () => void } {
        return {
            'click:button#set-name': this.onButtonSetNameClick.bind(this),
            'click:button#set-age': this.onButtonSetAgeClick.bind(this),
            'click:button#save': this.onButtonSaveClick.bind(this),
            'mouseenter:h1': this.onHeaderHover.bind(this)
        };
    }

    override template(): string {
        return `<div>
            <hr />
            <input id='input-name' placeholder='${this.model.get('name')}' />
            <button id='set-name'>Update name</button>
            <div>
                <button id='set-age'>Set random age</button>
                <button id='save'>Save</button>
            </div>
        </div>`;
    }

    private onHeaderHover(): void {
        console.log('Header hover');
    }

    private onButtonSetNameClick(): void {
        const nameInputElement = this.parent.querySelector<HTMLInputElement>('input#input-name');
        if (nameInputElement) {
            const newName = nameInputElement.value;
            this.model.set({ name: newName });
        }
    }

    private onButtonSetAgeClick(): void {
        const newAge = randomInt(10, 80);
        console.log(`Setting age to ${newAge}`);
        this.model.set({age: newAge});
    }

    private onButtonSaveClick(): void {
        this.model.save();
    }
}

export { UserForm };