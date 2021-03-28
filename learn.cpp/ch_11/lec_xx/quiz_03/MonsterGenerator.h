#pragma once

#include "Monster.h"

#include <array>
#include <string>


namespace ch_11_lecture_xx_quiz_03 {
    class MonsterGenerator {
    public:
        static Monster generateMonster();

    private:
        static const int s_minHp { 1 };
        static const int s_maxHp { 100 };

        static constexpr std::array<std::string_view, Monster::max_monster_types> s_names {
          "Firebreath",
          "GooGoo",
          "Baba",
          "Grunt",
          "Bones",
          "Trolly",
          "Dracula",
          "Brainless",
        };

        static constexpr std::array<std::string_view , Monster::max_monster_types> s_roars {
            "*wraaah*",
            "*bleah*",
            "*graah*",
            "*oooh*",
            "*rattle*",
            "*noooh*",
            "*woohoo*",
            "*maaah*",
        };

        static int rollDice(int min, int max);
    };
}



