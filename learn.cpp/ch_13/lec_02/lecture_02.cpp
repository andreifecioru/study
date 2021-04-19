#include "lecture_02.h"
#include "Cents.h"
#include "MinMax.h"

#include <iostream>


namespace ch_13_lecture_02 {
    int run() {
        Cents c1 { 10 };
        Cents c2 { 20 };

        std::cout << c1 + c2 << std::endl;
        std::cout << c1 + 10 << std::endl;
        std::cout << c1 + 10.0 << std::endl;
        std::cout << 10.0 + c1 << std::endl;

        std::cout << "\n===============\n";

        MinMax mm1 { 0, 0 };
        MinMax mm3 { -12, 5 };
        MinMax mm2 { 22 + mm1 + 1 + 10 + -1 + 15 + -10 + mm3 };

        std::cout << mm2 << std::endl;


        return EXIT_SUCCESS;
    }

}
