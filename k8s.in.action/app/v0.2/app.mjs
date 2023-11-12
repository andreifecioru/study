import http from 'http';
import os from 'os';
import readline from 'readline';

import { APP_VERSION, CONTENT_TYPES, ENCODINGS, LISTEN_PORT } from './lib/consts.mjs';
import * as utils from './lib/utils.mjs';

let statusMessage = '';

function showBanner() {

  console.log(`Kubernetes Demo App (v${APP_VERSION})`);
  console.log('----------------------------');
  console.log('Server is starting...');
  console.log(`Local hostname: ${os.hostname()}`);
  console.log(`Listening port: ${LISTEN_PORT}`);
}

const handler = function(request, response) {
  const clientIP = request.connection.remoteAddress;
  console.log(`Received request for ${request.url} from ${clientIP}.`);

  const  templateData= {
    hostname: os.hostname(),
    clientIP: clientIP,
    statusMessage: statusMessage,
  }

  switch(request.url) {
    case '/':
      if (utils.isWebBrowser(request)) {
        response.writeHead(302, {'Location': '/html'});
        response.write('Redirecting to html version...');
        response.end();
        return;
      }

    // text-based clients fall-through here
    case '/text':
      return utils.renderFile(response, 'assets/index.txt', CONTENT_TYPES.TEXT, templateData);
    case '/html':
      return utils.renderFile(response, 'assets/index.html', CONTENT_TYPES.HTML, templateData);
    case '/stylesheet.css':
      return utils.sendFile(response, 'assets/stylesheet.css', CONTENT_TYPES.CSS);
    case '/javascript.js':
      return utils.sendFile(response, 'assets/javascript.js', CONTENT_TYPES.JS);
    case '/favicon.ico':
      return utils.sendFile(response, 'assets/favicon.ico', CONTENT_TYPES.ICON);
    case '/cover.png':
      return utils.sendFile(response, 'assets/cover.png', CONTENT_TYPES.PNG);

    default:
      return utils.sendResponse(response, 404, CONTENT_TYPES.TEXT, ENCODINGS.UTF8, `${request.url} not found`);
  }
};

showBanner();

// start the HTTP server
const server = http.createServer(handler);
server.listen(LISTEN_PORT);

// listen on STDIN input
readline.createInterface({
  input: process.stdin,
  output: process.stdout,
  terminal: false
}).on('line', (line) => {
  if (line.trim() === '') {
    console.error("Enter new status message and press ENTER.");
  } else {
    statusMessage = line;
    console.log(`Status message set to: ${statusMessage}`)
  }
});

// handle the TERM signal
process.on('SIGTERM', () => {
  console.log("Received SIGTERM. Server shutting donw...");
  server.close(() => process.exit(0));
});