import fs from 'fs-extra';

import { countPrimes } from './lib.mjs';

function countPrimesInFile(filePath) {
  fs.readFile(filePath)
    .then(content => content.toString())
    .then(content => content.split('\n'))
    .then(lines => lines.map(countPrimes))
    .then(promises => Promise.all(promises))
    .then(counts => console.log(counts))
    .catch(error => console.log(error))
}
console.log(process.cwd());
countPrimesInFile('./rediscover.js/primes-service/data/valid_file.txt');
countPrimesInFile('./rediscover.js/primes-service/data/invalid_file.txt');
