import os from 'os';
import fs from 'fs';

import { APP_VERSION, ENCODINGS } from './consts.mjs';

function sendResponse(response, status, contentType, encoding, body) {
  response.writeHead(status, {'Content-Type': contentType});
  response.write(body, encoding);
  response.end();
}

function renderFile(request, response, path, contentType) {
  const template = fs.readFileSync(path, ENCODINGS.UTF8);

  const tokens = new Map([
    ['{{hostname}}', os.hostname()],
    ['{{clientIP}}', request.connection.remoteAddress],
    ['{{version}}', APP_VERSION]
  ]);

  const body = template.replace(
    new RegExp([...tokens.keys()].join('|'), 'g'),
    matched => tokens.get(matched)
  );

  sendResponse(response, 200, contentType, ENCODINGS.UTF8, body);
}

function sendFile(request, response, path, contentType) {
  const body = fs.readFileSync(path, ENCODINGS.BINARY);
  sendResponse(response, 200, contentType, ENCODINGS.BINARY, body);
}

// guess if the client is a browser or a text-based tool such as curl
function isWebBrowser(request) {
  const acceptHeader = request.headers.accept || '*/*';
  console.log(`[DEBUG] Accept header: ${acceptHeader}`);
  return acceptHeader.startsWith('text/html');
}

export {
  sendResponse,
  renderFile,
  sendFile,
  isWebBrowser
}