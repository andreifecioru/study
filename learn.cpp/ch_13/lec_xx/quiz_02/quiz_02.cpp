#include "quiz_02.h"

#include "Average.h"

#include <iostream>


namespace ch_13_lecture_xx_quiz_02 {
    int run() {
        Average avg {};


        avg += 4;
        std::cout << avg << std::endl;

        avg += 8;
        std::cout << avg << std::endl;

        avg += 24;
        std::cout << avg << std::endl;

        avg += -10;
        std::cout << avg << std::endl;

        (avg += 6) += 10;
        std::cout << avg << std::endl;

        Average copy { avg };
        std::cout << copy << std::endl;

        return EXIT_SUCCESS;
    }
}
