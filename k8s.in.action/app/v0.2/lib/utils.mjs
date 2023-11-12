import fs from 'fs';

import { APP_VERSION, ENCODINGS } from './consts.mjs';

function sendResponse(response, status, contentType, encoding, body) {
  response.writeHead(status, {'Content-Type': contentType});
  response.write(body, encoding);
  response.end();
}

function renderFile(response, path, contentType, templateData) {
  const template = fs.readFileSync(path, ENCODINGS.UTF8);

  const tokens = new Map([
    ['{{hostname}}', templateData.hostname],
    ['{{clientIP}}', templateData.clientIP],
    ['{{version}}', APP_VERSION],
    ['{{statusMessage}}', templateData.statusMessage]
  ]);

  const body = template.replace(
    new RegExp([...tokens.keys()].join('|'), 'g'),
    matched => tokens.get(matched)
  );

  sendResponse(response, 200, contentType, ENCODINGS.UTF8, body);
}

function sendFile(response, path, contentType) {
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