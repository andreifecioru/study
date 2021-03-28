#include "quiz_01.h"
#include "Point3D.h"
#include "Vector3D.h"

#include <iostream>


namespace ch_11_lecture_14_quiz_01 {
    namespace {}

    int run() {
        Point3D p1 { 10, 20, 30 };
        std::cout << p1 << std::endl;

        Vector3D v1 { 5, 5, 5 };
        std::cout << v1 << std::endl;

        p1.move(v1);
        std::cout << p1 << std::endl;

        return EXIT_SUCCESS;
    }
}
