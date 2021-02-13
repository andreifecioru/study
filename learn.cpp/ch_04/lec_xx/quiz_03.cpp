#include "quiz_03.h"

#include <iostream>


namespace ch_04_lecture_xx_quiz_03 {
    using namespace std;

    namespace {
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
    }

    int run() {
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
}
