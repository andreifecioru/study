import { User } from './User';

const user = new User();

console.log(`Name: ${user.name}`);
console.log(`Location:\n  - LAT: ${user.location.lat}\n  - LNG: ${user.location.lng}`);