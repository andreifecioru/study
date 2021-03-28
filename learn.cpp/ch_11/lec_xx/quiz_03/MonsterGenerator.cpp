#include "MonsterGenerator.h"
#include "Monster.h"

#include <random>


namespace ch_11_lecture_xx_quiz_03 {

    int MonsterGenerator::rollDice(const int min, const int max) {
        static std::random_device rd {};
        static std::mt19937 engine { rd() };
        static std::uniform_int_distribution<int> dist { min, max };

        return dist(engine);
    }

    Monster MonsterGenerator::generateMonster() {
        return Monster {
            static_cast<Monster::Type>(rollDice(0, Monster::max_monster_types - 1)),
            std::string(s_names[rollDice(0, Monster::max_monster_types - 1)]),
            std::string(s_roars[rollDice(0, Monster::max_monster_types - 1)]),
            rollDice(s_minHp, s_maxHp)
        };
    }
}