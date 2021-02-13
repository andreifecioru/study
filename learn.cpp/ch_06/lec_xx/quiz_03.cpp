#include "quiz_03.h"

#include <iostream>


namespace ch_06_lecture_xx_quiz_03 {
    using namespace std;

    namespace {
        bool passOrFail() {
            static int state {0};
            return (++state <= 3) ;
        }
    }

    int run() {
        std::cout << "User #1: " << (passOrFail() ? "Pass" : "Fail") << '\n';
        std::cout << "User #2: " << (passOrFail() ? "Pass" : "Fail") << '\n';
        std::cout << "User #3: " << (passOrFail() ? "Pass" : "Fail") << '\n';
        std::cout << "User #4: " << (passOrFail() ? "Pass" : "Fail") << '\n';
        std::cout << "User #5: " << (passOrFail() ? "Pass" : "Fail") << '\n';

        return EXIT_SUCCESS;
    }
}
