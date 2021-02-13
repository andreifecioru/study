#include "quiz_02.h"

#include <iostream>


namespace ch_09_lecture_03_quiz_02 {
    namespace {
        int getInteger() {
            while (true) {
                int userInput{};

                std::cout << "Enter a number (1-9): ";
                std::cin >> userInput;
                if (std::cin.fail()) {
                    std::cin.clear();
                    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

                } else {
                    if (userInput >= 1 && userInput <= 9)
                        return userInput;
                }
            };
        }

        void printArray(const int array[], const size_t size) {
            std::cout << "[ ";
            for (auto i {0}; i < size; ++i) {
                std::cout << array[i] << ' ';
            }
            std::cout << "]\n";
        }
    }

    int run() {
        constexpr int array[]{ 4, 6, 7, 3, 8, 2, 1, 9, 5 };

        int userInput { getInteger() };

        printArray(array, std::size(array));
        std::cout << "array[" << userInput << "] = " << array[userInput] << std::endl;

        return EXIT_SUCCESS;
    }

}
