#include <cstdlib>
#include <iostream>

#include "io.hpp"

using namespace std;

int main() {
    double op1{getOperand()};
    double op2{getOperand()};
    char operation{getOperation()};

    double result{};
    switch (operation) {
        case '+':
            result = op1 + op2;
            break;

        case '-':
            result = op1 - op2;
            break;

        case '*':
            result = op1 * op2;
            break;

        case '/':
            result = op1 / op2;
            break;

        default:
            cout << "Invalid operation: " << operation;
            return EXIT_FAILURE;
    }

    showResult(op1, op2, operation, result);

    return EXIT_SUCCESS;
}
