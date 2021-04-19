#include "quiz_01.h"

#include "Fraction.h"

#include <iostream>


namespace ch_13_lecture_02_quiz_01 {
    int run() {
        Fraction f1 { 10, 15 };
        Fraction f2 {  3, 4 };

        std::cout << f1 << std::endl;
        std::cout << f2 << std::endl;
        std::cout << f1 * f2 << std::endl;

        std::cout << f1 * 2 << std::endl;
        std::cout << 3 * f1 << std::endl;

        return EXIT_SUCCESS;
    }
}
