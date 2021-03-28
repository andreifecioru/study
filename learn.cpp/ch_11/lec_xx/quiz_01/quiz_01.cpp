#include "quiz_01.h"
#include "Point2D.h"

#include <iostream>

namespace ch_11_lecture_xx_quiz_01 {
    namespace {}

    int run() {
        Point2D p1 {};
        Point2D p2 { 3.0, 4.0 };
        std::cout << p1 << std::endl;
        std::cout << p2 << std::endl;

        std::cout << "Distance between two points: " << p1.distanceTo(p2) << std::endl;
        std::cout << "Distance between two points: " << distanceBetween(p1, p2) << std::endl;

        return EXIT_SUCCESS;
    }
}
