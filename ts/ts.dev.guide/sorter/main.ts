import {LinkedList} from "./lib/LinkedList";
import {Person} from "./lib/Person";

const numsList = new LinkedList<Person>();


numsList.push(new Person("John", 25));
numsList.push(new Person("Jane", 21));

numsList.asArray().forEach(p => console.log(p));
console.log("======================");
numsList.sort().asArray().forEach(p =>console.log(p));