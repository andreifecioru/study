function my_func() {
  // a `;` will be inserted after `first`
  let first
  second = 1;

  // `first` is undefied
  console.log(first);
  // `second` becomes global
  console.log(second);
}

my_func();

console.log(second);



function my_func_1() {
  let number = 2;

  // this returns undefined
  return
    number;
}

const result = my_func_1();
console.log(result)