#include "quiz_01.h"

#include <iostream>

namespace ch_10_lecture_09_quiz_01 {
    namespace {

        double add(double op1, double op2) { return op1 + op2; }
        double subtract(double op1, double op2)  { return op1 - op2; }
        double multiply(double op1, double op2) { return op1 * op2; }
        double divide(double op1, double op2) { return op1 / op2; }

        const OperationTable opTable {
                {OperationType::add, add},
                {OperationType::subtract, subtract},
                {OperationType::multiply, multiply},
                {OperationType::divide, divide},
        };

        void ignoreLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        double getInputOperand(const std::string& prompt) {
            std::cout << prompt;
            double userInput{};
            std::cin >> userInput;
            ignoreLine();

            return userInput;
        }

        OperationType getInputOperator() {
            do {
                std::cout << "Enter the operator (one of [+,-,*,/]): ";

                char userInput{};
                std::cin >> userInput;
                ignoreLine();

                switch (userInput) {
                    case OpType2Int(OperationType::add):
                    case OpType2Int(OperationType::subtract):
                    case OpType2Int(OperationType::multiply):
                    case OpType2Int(OperationType::divide):
                        return static_cast<OperationType>(userInput);
                    default:
                        std::cout << "Invalid input. Try again..." << std::endl;
                        continue;
                }

            } while(true);
        }
    }

    int run() {
        double op1 { getInputOperand("Enter the 1st operand: ") };
        double op2 { getInputOperand("Enter the 2nd operand: ") };
        OperationType op { getInputOperator() };

        double result = opTable.at(op)(op1, op2);

        std::cout << op1 << ' ' << OpType2Char(op) << ' ' << op2 << " = " << result << std::endl;

        return EXIT_SUCCESS;
    }
}
