const {shell} = require('electron');

const linksSection = document.querySelector('.links');
const errorMessage = document.querySelector('.error-message');
const newLinkForm = document.querySelector('.new-link-form');
const newLinkUrl = document.querySelector('.new-link-url');
const newLinkSubmit = document.querySelector('.new-link-submit');
const clearStorageButton = document.querySelector('.clear-storage');

const clearForm = () => {
  newLinkUrl.value = null;
};

const validateResponse = (response) => {
  if (response.ok) { return response; }
  throw new Error(`Status code of ${response.status} ${response.statusText}`);
};
const domParser = new DOMParser();
const parseResponse = text => domParser.parseFromString(text, 'text/html');
const findTitle = dom => dom.querySelector('title').innerText;
const storeLink = (title, url) => localStorage.setItem(url, JSON.stringify({title: title, url: url}));
const loadLinks = () => Object.keys(localStorage).map(key => JSON.parse(localStorage.getItem(key)));
const linkToHtml = (link) => `
  <div class="link">
    <h3>${link.title}</h3>
    <p>
      <a href="${link.url}">${link.url}</a>
    </p>
  </div>
`;
const renderLinks = () => linksSection.innerHTML = loadLinks().map(linkToHtml).join("");
const onFetchError = (error, url) => {
  errorMessage.innerHTML = `There was an issue adding "${url}": ${error.message}`.trim();
  setTimeout(() => errorMessage.innerHTML = null, 5000);
};

renderLinks();

newLinkUrl.addEventListener('keyup', () => {
  newLinkSubmit.disabled = !newLinkUrl.validity.valid;
});

newLinkForm.addEventListener('submit', (event) => {
  event.preventDefault();

  const url = newLinkUrl.value;

  console.log(url);

  fetch(url)
    .then(validateResponse)
    .then(response => response.text())
    .then(parseResponse)
    .then(findTitle)
    .then(title => storeLink(title, url))
    .then(clearForm)
    .then(renderLinks)
    .catch(error => onFetchError(error, url));
});

clearStorageButton.addEventListener('click', () => {
  localStorage.clear();
  linksSection.innerHTML = "";
});

linksSection.addEventListener('click', (event) => {
  if (event.target.href) {
    event.preventDefault();
    shell.openExternal(event.target.href);
  }
});