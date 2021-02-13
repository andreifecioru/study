#include "quiz_01.h"

#include <iostream>
#include <algorithm>

namespace ch_09_lecture_14_quiz_01 {
    namespace {
        void ignoreLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        size_t getNameCount() {
            std::cout << "How many names would you like to enter? ";

            size_t nameCount{};
            std::cin >> nameCount;
            ignoreLine();

            return nameCount;
        }

        std::string* getNames(const size_t nameCount) {
            std::string* const names { new std::string[nameCount]{} };
            for (auto i{0}; i < nameCount; ++i) {
                std::cout << "Enter name #" << i << ": ";
                std::getline(std::cin, names[i]);
            }

            return names;
        }

        void listNames(const std::string* const names, const size_t nameCount) {
            std::cout << "\n\nHere is your list: \n";
            for(auto i{0}; i < nameCount; ++i) {
                std::cout << "Name #" << i << ": " << names[i] << '\n';
            }
            std::cout << std::endl;
        }
    }

    int run() {
        const size_t nameCount { getNameCount() };
        std::string* const names { getNames(nameCount) };

        listNames(names, nameCount);

        std::sort(names, names + nameCount);
        listNames(names, nameCount);

        delete [] names;

        return EXIT_SUCCESS;
    }
}
