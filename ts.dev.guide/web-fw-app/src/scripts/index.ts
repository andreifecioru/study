import { User } from './models/User';

const user = User.empty()
user.on('change', () => {
  user.print();
});

user.set({ name: 'John', age: 22 });
user.save();

setTimeout(() => {
  user.fetch();
}, 50);

