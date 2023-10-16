import cluster from 'cluster';
import http from 'http';
import url from 'url';
import querystring from 'querystring';

import { allPrimesLessThan } from './lib.mjs';

const port = 8084;
const PROCESS_COUNT = 8;

const handler = function(request, response) {
  if (request.url.toString().includes('favicon.ico')) {
    console.log('Handling request for fav-icon');

    response.writeHead(200, { 'Content-Type': 'text/plain'});
    return response.end('');
  }

  console.log(`Handling request for: ${request.url}`);
  const params = querystring.parse(url.parse(request.url).query);
  const number = parseInt(params.number);
  const count = allPrimesLessThan(number).length

  response.writeHead(200, { 'Content-Type': 'text/plain'});
  return response.end(`${count}`);
}

if (cluster.isPrimary) {
  console.log('Starting server...');
  for(let i = 0; i < PROCESS_COUNT; i++) {
    cluster.fork();
  } 
} else {
  http.createServer(handler).listen(port);
}