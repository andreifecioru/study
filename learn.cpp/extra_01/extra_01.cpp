#include "extra_01.h"

#include <iostream>
#include <vector>


namespace extra_01 {
    namespace {

        struct Person {
            std::string firstName;
            std::string lastName;
        };

    }

    int run() {
        std::vector<Person> persons {
                {.firstName = "Andrei", .lastName = "Fecioru"},
                {.firstName = "Ion", .lastName = "Popescu"},
                {.firstName = "Mihai", .lastName = "Ionescu"},
        };

        for (auto const& [first, last] : persons) {
            std::cout << "First name: " << first << '\n'
                      << "Last name: " << last << '\n'
                      << std::endl;
        }

        return EXIT_SUCCESS;
    }
}
