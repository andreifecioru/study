#include "quiz_03.h"
#include "IntArray.h"

#include <iostream>


namespace ch_13_lecture_xx_quiz_03 {
    namespace {
        IntArray fillArray() {
            IntArray a { 5 };

            a[0] = 5;
            a[1] = 8;
            a[2] = 2;
            a[3] = 3;
            a[4] = 6;

            return a;
        }
    }
    int run() {
        IntArray a { fillArray() };
        std::cout << a << std::endl;

        auto &ref { a };
        a = ref;

        IntArray b { 1 };
        b = a;

        std::cout << b << std::endl;

        return EXIT_SUCCESS;
    }

}
