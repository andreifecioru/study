#include "quiz_01.h"
#include "StudentGrades.h"

#include <iostream>


namespace ch_13_lecture_09_quiz_01 {
    int run() {
        StudentGrades grades {};
        grades["Andrei"] = 'A';
        grades["Radu"] = 'B',
        grades["Cristi"] = 'C';

        std::cout << grades << std::endl;

        const StudentGrades grades_2 { grades };
        std::cout << grades_2["Luca"] << std::endl;

        return EXIT_SUCCESS;
    }

}
