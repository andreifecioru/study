#include "quiz_01.h"

#include <iostream>
#include <iomanip>

namespace ch_08_lecture_01_quiz_01 {
    namespace {
        void ignoreLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        std::string getFullName() {
            std::cout << "Enter your full name: ";

            std::string fullName {};
            std::getline(std::cin, fullName);
//            ignoreLine();

            return fullName;
        }

        int getAge() {
            std::cout << "Enter you age: ";

            int age {};
            std::cin >> age;
            ignoreLine();

            return age;
        }
    }

    int run() {
        std::string fullName { getFullName() };
        int age { getAge() };

        double avgAgePerLetter = static_cast<double>(age) / fullName.length();

        std::cout << std::setprecision(3);
        std::cout << "You've lived " << avgAgePerLetter << " years for each letter in your name.";

        return EXIT_SUCCESS;
    }
}
