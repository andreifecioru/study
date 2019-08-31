#include <iostream>
#include <cstdlib>

using namespace std;

int main() {

    cout << "Enter an integer: ";

    int32_t op1{};
    cin >> op1;

    cout << "Enter another integer: ";

    int32_t op2{};
    cin >> op2;

    cout << op1 << " + " << op2 << " is " << op1 + op2 << endl;
    cout << op1 << " - " << op2 << " is " << op1 - op2 << endl;

    return EXIT_SUCCESS;
}