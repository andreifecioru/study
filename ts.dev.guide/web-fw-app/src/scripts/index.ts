import { Users } from './models/User';
import { UserEdit } from './views/UserEdit';
import { UserForm } from './views/UserForm';
import { UserShow } from './views/UserShow';

const rootElement = document.querySelector('#root');


const users = new Users();
users.on('change', () => {
  users.entries.forEach(user => {
    user.print();
  });

  const firstUser = users.entries[0]
  if (firstUser) {
    if (rootElement) {
      const userEditView = new UserEdit(rootElement, firstUser)
      userEditView.render();
    }

    const userShowElement = document.querySelector(".user-show");
    if (userShowElement) {
      const userShowView = new UserShow(userShowElement, firstUser);
      userShowView.render();
    }

    const userFormElement = document.querySelector(".user-form");
    if (userFormElement) {
      const userFromView = new UserForm(userFormElement, firstUser);
      userFromView.render();
    }
  }
});


users.fetchAll();
