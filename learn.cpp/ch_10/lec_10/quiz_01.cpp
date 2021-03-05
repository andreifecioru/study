#include "quiz_01.h"

#include <iostream>
#include <cassert>

namespace ch_10_lecture_10_quiz_01 {
    namespace {
        long factorial(const long num) {
            assert(num >= 0);

            if (num <= 1)
                return 1;

            return num * factorial(num - 1);
        }
    }

    int run() {
        for (auto i{1}; i <=7; i++) {
            std::cout << "Factorial of " << i << " is " << factorial(i) << '\n';
        }
        std::cout << std::endl;

        return EXIT_SUCCESS;
    }

}
