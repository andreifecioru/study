'use strict';

function my_func() {
  console.log(new.target);
}

my_func();
new my_func();