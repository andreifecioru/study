import { ListView } from './ListView';
import { User, UserProps } from '../models/User';

class UserListView extends ListView<User, UserProps> {
    override renderItem(item: User): string {
        return `<li>Name: ${item.get('name')}. Age: ${item.get('age')}</li>`
    }
}

export { UserListView };