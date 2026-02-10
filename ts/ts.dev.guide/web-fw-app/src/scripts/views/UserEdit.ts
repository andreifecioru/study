import { View } from './View';
import { UserForm } from './UserForm';
import { UserShow } from './UserShow';
import { BaseModel } from '../models/Model';
import { UserProps } from '../models/User';


class UserEdit extends View<UserProps> {
    constructor(readonly parent: Element, readonly model: BaseModel<UserProps>) {
        super(parent, model);
    }

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

    protected override onRender(): void {
        new UserShow(this.regions.userShow, this.model).render();
        new UserForm(this.regions.userForm, this.model).render();
    }
}

export { UserEdit };