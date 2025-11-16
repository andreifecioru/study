import { Users } from './models/User';
import { UserForm } from './views/UserForm';

const rootElement = document.getElementById("root");

const users = new Users();
users.on('change', () => {
  users.entries.forEach(user => {
    user.print();
  });

  const firstUser = users.entries[0]
  if (firstUser && rootElement) {
    const userForm = new UserForm(rootElement, firstUser);
    userForm.render();
  }
});

users.fetchAll();
