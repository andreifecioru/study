import http from 'http';
import os from 'os';

import { APP_VERSION, CONTENT_TYPES, ENCODINGS, LISTEN_PORT } from './lib/consts.mjs';
import * as utils from './lib/utils.mjs';

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
      return utils.renderFile(request, response, 'assets/index.txt', CONTENT_TYPES.TEXT);
    case '/html':
      return utils.renderFile(request, response, 'assets/index.html', CONTENT_TYPES.HTML);
    case '/stylesheet.css':
      return utils.sendFile(request, response, 'assets/stylesheet.css', CONTENT_TYPES.CSS);
    case '/javascript.js':
      return utils.sendFile(request, response, 'assets/javascript.js', CONTENT_TYPES.JS);
    case '/favicon.ico':
      return utils.sendFile(request, response, 'assets/favicon.ico', CONTENT_TYPES.ICON);
    case '/cover.png':
      return utils.sendFile(request, response, 'assets/cover.png', CONTENT_TYPES.PNG);

    default:
      return utils.sendResponse(response, 404, CONTENT_TYPES.TEXT, ENCODINGS.UTF8, `${request.url} not found`);
  }
};

showBanner();
const server = http.createServer(handler);
server.listen(LISTEN_PORT);