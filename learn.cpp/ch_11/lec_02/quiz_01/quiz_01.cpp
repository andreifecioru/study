#include "quiz_01.h"
#include "IntPair.h"

#include <iostream>


namespace ch_11_lecture_02_quiz_01 {
    namespace {

    }

    int run() {
        IntPair p1;
        p1.set(1, 1);

        IntPair p2 { 2, 2 };

        p1.print();
        p2.print();

        return EXIT_SUCCESS;
    }
}
