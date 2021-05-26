#include <iostream>
#include <cstdlib>

#include <maths/ops.hpp>

int main() {
  using namespace std;
  using maths::ops::add;

  cout << "Enter a number: ";

  double op1 {};
  cin >> op1;

  cout << "Enter another number: ";

  double op2 {};
  cin >> op2;

  double result { add(op1, op2) };

  cout << "Result is: " << result << endl;

  return EXIT_SUCCESS;
}
