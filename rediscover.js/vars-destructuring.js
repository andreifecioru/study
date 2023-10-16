(function() {
  'use strict';

  const nums = [1, 2, 3, 4];

  console.log(nums);

  const [a, b, c, d, e, ...rest] = nums;

  console.log(a);
  console.log(b);
  console.log(c);
  console.log(d);
  console.log(e);
  console.log(rest);

  console.log(parseInt("10"));
  console.log(parseInt("10.1"));
  console.log(parseFloat("10.1"));
  console.log(parseFloat("andrei")); // NaN

  const { lat, lon, favourite: fav = true} = {lat: 84.5, lon: -114.25, favourite: false};
  console.log(`Favourite: ${fav}`);

})();