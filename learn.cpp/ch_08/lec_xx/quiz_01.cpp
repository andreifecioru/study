#include "quiz_01.h"

#include <iostream>


namespace ch_08_lecture_xx_quiz_01 {

    namespace {
        std::string monsterType2String(MonsterType monsterType) {
            switch (monsterType) {
                case MonsterType::ogre: return "Ogre";
                case MonsterType::dragon: return "Dragon";
                case MonsterType::orc: return "Orc";
                case MonsterType::giant_spider: return "Giant Spider";
                case MonsterType::slime: return "Slime";
                default:
                    static_assert("Invalid monster type.");
            }
        }

        void printMonster(const Monster& monster) {
            std::cout << "This " << monsterType2String(monster.type)
                      << " is named " << monster.name
                      << " and has " << monster.hp << " health."
                      << std::endl;
        }
    }

    int run() {
        Monster ogre { MonsterType::ogre, 145, "Torg" };
        Monster slime { MonsterType::slime, 23, "Blurp"};

        printMonster(ogre);
        printMonster(slime);

        return EXIT_SUCCESS;
    }
}
