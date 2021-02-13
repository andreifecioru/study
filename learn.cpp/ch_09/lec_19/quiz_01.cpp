#include "quiz_01.h"

#include <iostream>

namespace ch_05_lecture_19_quiz_01 {

    namespace {
        std::string getName() {
            std::cout << "Enter a name: ";

            std::string name{};
            std::getline(std::cin, name);

            return name;
        }
    }

    int run() {
        constexpr std::string_view names[] = { "Alex", "Betty", "Caroline", "Dave", "Emily", "Fred", "Greg", "Holly" };
        constexpr size_t nameCount { std::size(names) };

        std::cout << "We have " << nameCount << " names." << std::endl;

        const std::string targetName { getName() };

        bool found {false};
        for (const auto name: names) {
            if (name == targetName) {
                found = true;
                break;
            }
        }

        if (found)
            std::cout << targetName << " was found.";
        else
            std::cout << targetName << " was NOT found.";

        return EXIT_SUCCESS;
    }

}
