#pragma once

#include <iostream>


namespace ch_08_lecture_xx_quiz_01 {

    enum class MonsterType {
        ogre,
        dragon,
        orc,
        giant_spider,
        slime
    };

    struct Monster {
        MonsterType type {MonsterType::ogre};
        int hp {100};
        std::string name {};
    };


    int run();
}