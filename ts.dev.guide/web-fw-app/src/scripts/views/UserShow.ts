import { View } from './View';
import { UserProps } from '../models/User';


class UserShow extends View<UserProps> {
    protected eventsMap(): { [key: string]: () => void } {
        return {};
    }
    protected template(): string {
        return `<div>
            <h1>User details</h1>
            <p>User ID: ${this.model.get('id')}</p>
            <p>User name: ${this.model.get('name')}</p>
            <p>User age: ${this.model.get('age')}</p>
        </div>`;
    }
}

export { UserShow };