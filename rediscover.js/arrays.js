(function() {
  // Empty slots in arrays
  const values = [/* empty */, 'a', /* empty */, 'b', 'c', /* empty*/, 'd', /* empty */, ];

  console.log(`Lenght is: ${values.length}`);
  for (const [i, v] of values.entries()) {
    // empty slots when referenced directly have a value of 'undefined'
    console.log(`values[${i}] = ${v}`);
  }

  console.log('------------------');

  // in the HOFs defined on arrays, the empty slots are skipped altogether
  values
  .map((v) => v.toUpperCase())
  .forEach((v, i) => {
    console.log(`values[${i}] = ${v}`);
  });

  console.log('------------------');

  // create arrays FP style
  const nums = Array.from(new Array(10), (_, idx) => idx + 100);
  nums.forEach(v => console.log(v));

})();