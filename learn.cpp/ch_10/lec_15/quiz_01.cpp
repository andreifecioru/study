#include "quiz_01.h"

#include <iostream>
#include <array>
#include <algorithm>


namespace ch_10_lecture_15_quiz_01 {
    namespace {

        constexpr std::array<Student, 8> students { {
            { "Albert", 3},
            { "Ben", 5},
            { "Christine", 2},
            { "Dan", 8},
            { "Enchilada", 4},
            { "Francis", 1},
            { "Greg", 3},
            { "Hagrid", 5},

        } };

    }

    int run() {
        auto comparePoints = [](const Student& s1, const Student& s2) {
            return s1.points < s2.points;
        };
        const auto bestStudent = std::max_element(students.begin(), students.end(), comparePoints);

        std::cout << bestStudent->name << " is the best student." << std::endl;

        return EXIT_SUCCESS;
    }
}
