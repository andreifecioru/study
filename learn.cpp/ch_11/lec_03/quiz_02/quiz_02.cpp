#include "quiz_02.h"
#include "Point3D.h"

#include <iostream>


namespace ch_11_lecture_03_quiz_02 {
    namespace {
    }

    int run() {

        Point3D p1;
        p1.setValues(1, 2, 3);

        Point3D p2 { 1, 2, 3};
        Point3D p3 {3, 4, 5};

        std::cout << "p1: " << p1 << std::endl;
        std::cout << "p2: " << p2 << std::endl;
        std::cout << "p3: " << p3 << std::endl;

        if (p1 == p2) {
            std::cout << "p1 and p2 are equal" << std::endl;
        } else {
            std::cout << "p1 and p2 are not equal" << std::endl;
        }

        if (p1 == p3) {
            std::cout << "p1 and p3 are equal" << std::endl;
        } else {
            std::cout << "p1 and p3 are not equal" << std::endl;
        }


        return EXIT_SUCCESS;
    }
}
