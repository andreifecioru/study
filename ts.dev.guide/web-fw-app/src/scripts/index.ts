import { Users } from './models/User';
import { UserEdit } from './views/UserEdit';
import { UserListView } from './views/UserListView';

const userEditElement = document.querySelector('#user-edit');
const userListElement = document.querySelector('#user-list');


const users = new Users();
users.on('change', () => {
  users.entries.forEach(user => {
    user.print();
  });

  const firstUser = users.entries[0]
  if (firstUser) {
    if (userEditElement) {
      new UserEdit(userEditElement, firstUser).render();
    }

    if (userListElement) {
      new UserListView(userListElement, users.entries).render();
    }
  }
});


users.fetchAll();
