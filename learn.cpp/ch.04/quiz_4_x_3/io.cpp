#include "io.hpp"

#include <iostream>

using namespace std;

double getOperand() {
    cout << "Enter a double value: ";

    double operand{};
    cin >> operand;

    return operand;
}

char getOperation() {
    char operation{};

    do {
        cout << "Enter one of the following: +, -, *, / : ";
        cin >> operation;
    } while (
        operation != '+' && operation != '-' && operation != '*' && operation != '/');

    return operation;
}

void showResult(double op1, double op2, char operation, double result) {
    cout << op1 << ' ' << operation << ' ' << op2 << " is " << result << endl;
}