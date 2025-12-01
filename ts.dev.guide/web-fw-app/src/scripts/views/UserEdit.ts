import { View } from './View';
import { UserProps } from '../models/User';


class UserEdit extends View<UserProps> {
    protected override template(): string {
        return `
        <div>
            <div class='user-show'></div>
            <div class='user-form'></div>
        </div>
        `;
    }

    protected override regionMap(): { [key: string]: string } {
        return {
            'userShow': '.user-show',
            'userForm': '.user-form'
        }
    }
}

export { UserEdit };