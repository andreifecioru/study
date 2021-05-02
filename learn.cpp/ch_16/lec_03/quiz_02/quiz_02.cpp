#include "quiz_02.h"

#include "Teacher.h"
#include "Department.h"

#include <iostream>


namespace ch_16_lecture_03_quiz_02 {
    int run() {
        Teacher t1 { "Bob" };
        Teacher t2 { "Frank" };
        Teacher t3 { "Beth" };

        {
            Department department {};

            department.add(t1);
            department.add(t2);
            department.add(t3);

            std::cout << department << std::endl;
        }

        std::cout << t1 << std::endl;
        std::cout << t2 << std::endl;
        std::cout << t3 << std::endl;

        return EXIT_SUCCESS;
    }
}
