#include "lecture_07.h"
#include "add.h"
#include "io.h"

#include <iostream>


namespace ch_02_lecture_07 {
    using namespace std;

    int run() {
        int32_t op1{ getInteger() };
        int32_t op2{ getInteger() };

        showResult(add(op1, op2));

        return EXIT_SUCCESS;
    }
}
