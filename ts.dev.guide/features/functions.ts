const add = (x: number, y: number): number => {
  return x + y;
};
console.log(`10 + 15 = ${add(10, 15)}`);

function multiply(x: number, y: number): number {
  return x * y;
}

const subtract = function(x: number, y: number): number {
  return x - y;
}

// Use 'void' when the function does not return a value (a.k.a. Unit in Scala)
const logger = (message: string): void => {
  console.log(message);
}

// Use 'never' when the function throws an axception (a.k.a. Nothing in Scala)
const throwError = (message: string): never => {
  throw new Error(message);
}

const todayWeather = {
  date: new Date(),
  weather: 'sunny'
};

// ES2015 destructuring syntax
const logWeather_ES2015 = ({date, weather}) => {
  console.log(`Forecast for ${date} is ${weather}`);
};

// Destructuring in TS
const logWeather = ({date, weather} : {date: Date; weather: string}): void => {
  console.log(`Forecast for ${date} is ${weather}`);
};
logWeather(todayWeather);
