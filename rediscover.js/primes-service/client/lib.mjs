import request from 'request-promise';

const SERVICE_URL = (number) => `http://localhost:8084?number=${number}`

export async function countPrimes(number) {
  if (isNaN(number)) {
    return Promise.reject(`'${number}' is not a number`);
  }

  return request(SERVICE_URL(number))
    .then(count => `Number of primes less than ${number} is ${count}`);
}