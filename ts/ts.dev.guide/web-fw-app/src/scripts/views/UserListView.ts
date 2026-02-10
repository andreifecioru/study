import { ListView } from './ListView';
import { User, UserProps } from '../models/User';

class UserListView extends ListView<User, UserProps> {
    override renderItem(item: User): Element {
        const listItem = document.createElement('li');
        listItem.textContent = `Name: ${item.get('name')}. Age: ${item.get('age')}`;
        return listItem;
    }
}

export { UserListView };