#include "quiz_02.h"

#include <iostream>


namespace ch_09_lecture_02_quiz_02 {
    int run() {
        int numLegs[AnimalNames::max_animal_count] = {2, 4, 4, 4, 2, 0};

        std::cout << "The elephant has " << numLegs[AnimalNames::elephant] << " legs." << std::endl;

        return EXIT_SUCCESS;
    }
}
