function sqrtAsync(value) {
  if (value < 0) {
    return Promise.reject('Input argument must be positive');
  }

  if (value === 0) {
    return Promise.resolve(0);
  }

  return new Promise((resolve, reject) => {
    setTimeout(() => resolve(Math.sqrt(value)), 1000);
  });
}

function reportOnPromise(promise) {
  promise
    .then(value => console.log(`Result: ${value}`))
    .catch(err => console.log(`ERROR: ${err}`));
}

reportOnPromise(sqrtAsync(0));
reportOnPromise(sqrtAsync(100));
reportOnPromise(sqrtAsync(-1));
