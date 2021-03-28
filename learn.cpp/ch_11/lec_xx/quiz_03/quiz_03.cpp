#include "quiz_03.h"
#include "Monster.h"
#include "MonsterGenerator.h"

#include <iostream>


namespace ch_11_lecture_xx_quiz_03 {
    namespace {}

    int run() {
        Monster bones { Monster::Type::skeleton, "Bones", "*rattle*", 4 };
        std::cout << bones << std::endl;

        Monster monster { MonsterGenerator::generateMonster() };
        std::cout << monster << std::endl;

        return EXIT_SUCCESS;
    }
}
