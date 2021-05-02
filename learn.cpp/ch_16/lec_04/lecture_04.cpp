#include "lecture_04.h"
#include "Patient.h"
#include "Doctor.h"

#include <iostream>


namespace ch_16_lecture_04 {
    int run() {

        Patient james { "James" };
        Patient george { "George" };

        Doctor stevenson { "Stevenson" };
        Doctor smith { "Smith" };

        james.addDoctor(stevenson);
        james.addDoctor(smith);

        george.addDoctor(smith);

        std::cout << "=====[ Patients ]=====\n"
                  << james << "\n"
                  << george << std::endl;

        std::cout << "=====[ Doctors ]=====\n"
                  << stevenson << "\n"
                  << smith << std::endl;

        return EXIT_SUCCESS;
    }

}
