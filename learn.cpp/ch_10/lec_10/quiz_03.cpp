#include "quiz_03.h"

#include <iostream>
#include <array>

namespace ch_10_lecture_10_quiz_03 {
    namespace {
        void ignoreLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        unsigned int getInteger() {
            do {
                std::cout << "Enter a positive integer: ";

                int userInput{};
                std::cin >> userInput;
                ignoreLine();

                if (userInput < 0)
                    continue;

                return userInput;
            } while(true);
        }

        void intToBinary(const unsigned int num, char* const outBin, int bitIdx = 31) {
            if (bitIdx < 0) return;

            outBin[31 - bitIdx] = (num >= (1 << bitIdx)) ? '1' : '0';
            intToBinary(num >= (1 << bitIdx) ? num - (1 << bitIdx) : num, outBin, bitIdx - 1);
        }

        void printBinary(const char* const bin) {
            for (auto i{0}; i < 32; ++i) {
                std::cout << bin[i];
                int j {i + 1};
                if (j % 4 == 0 && j != 32)
                    std::cout << '\'';
            }
            std::cout << std::endl;
        }
    }

    int run() {
        char bin[33]{};

        intToBinary(getInteger(), bin);

        std::cout << "Binary translation: ";
        printBinary(bin);

        return EXIT_SUCCESS;
    }
}
