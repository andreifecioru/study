#include "quiz_02.h"

#include <iostream>
#include <vector>
#include <cassert>
#include <algorithm>


namespace ch_09_lecture_xx_quiz_02 {
    namespace {
        void ignoreInputLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        int getInteger(const std::string& prompt,
                       const int min = std::numeric_limits<int>::min(),
                       const int max = std::numeric_limits<int>::max()) {
            assert(min < max);

            int value{};
            bool done{false};

            do {
                std::cout << prompt;
                std::cin >> value;

                if (std::cin.fail()) {
                    std::cin.clear();
                    ignoreInputLine();
                } else {
                    done = (value >= min && value <= max);
                }
            } while (!done);

            return value;
        }

        void getStudentData(const int studentCount, std::vector<student>& students) {
            for (auto i{0}; i < studentCount; ++i) {
                std::cout << "Enter data for student #" << i + 1 << ": \n"
                          << "\tname: ";

                ignoreInputLine();
                std::string name{};
                std::getline(std::cin, name);

                students.push_back({ name, getInteger("\tgrade: ", 0, 100) });
            }

            std::cout << std::endl;
        }

        void displayStudentData(const std::vector<student>& students) {
            std::for_each(students.begin(), students.end(), [](const student& s){
                std::cout << s.name << " got a grade of " << s.grade << '\n';
            });

            std::cout << std::endl;
        }
    }

    int run() {
        std::vector<student> students{};

        int studentCount { getInteger("Enter the number of students in the class: ", 0) };
        getStudentData(studentCount, students);
        displayStudentData(students);

        std::sort(students.begin(), students.end(),
                  [](const student& s1, const student& s2) { return s1.grade > s2.grade; });
        displayStudentData(students);

        return EXIT_SUCCESS;
    }

}
