#include "quiz_02.h"

#include <iostream>

namespace ch_10_lecture_10_quiz_02 {
    namespace {

        int sumOfDigits(const int num) {
            if (num < 10)
                return num;

            return num % 10 + sumOfDigits(num / 10);
        }

    }

    int run() {
        std::cout << "Sum of digits for 93427 is " << sumOfDigits(93427) << std::endl;

        return EXIT_SUCCESS;
    }
}
