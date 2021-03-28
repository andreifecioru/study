#include "lecture_14.h"
#include "StaticConstrLambda.h"
#include "StaticConstrInnerClass.h"

#include <iostream>
#include <algorithm>

namespace ch_11_lecture_14 {
    namespace { }

    int run() {
        const StaticConstrLambda::values_t& values { StaticConstrLambda::getValues() };

        std::for_each(values.begin(), values.end(), [](auto value){
            std::cout << value << ' ';
        });
        std::cout << std::endl;

        std::cout << "\n==================\n";

        const StaticConstrInnerClass::values_t& values1 { StaticConstrInnerClass::getValues() };

        std::for_each(values1.begin(), values1.end(), [](auto value){
            std::cout << value << ' ';
        });
        std::cout << std::endl;

        return EXIT_SUCCESS;
    }
}
