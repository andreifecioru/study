#include "quiz_04.h"

#include <iostream>

namespace ch_08_lecture_xx_quiz_04 {
    namespace {
        void displayString(const char* const in_str) {
            for (auto c {in_str}; *c; ++c) {
                std::cout << std::string{*c} << ' ';
            }
            std::cout << std::endl;
        }
    }

    int run() {
        displayString("Hello");

        return EXIT_SUCCESS;
    }
}
