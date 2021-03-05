#include "quiz_02.h"

#include <iostream>
#include <algorithm>
#include <array>

namespace ch_10_lecture_15_quiz_02 {
    namespace {
        std::array<Season, 4> seasons{{
                {"Spring", 285.0},
                {"Summer", 296.0},
                {"Fall", 288.0},
                {"Winter", 263.0},
        }};
    }

    int run() {
        auto byAvgTemp = [](const Season& s1, const Season &s2) {
            return s1.avgTemp < s2.avgTemp;
        };

        std::sort(seasons.begin(), seasons.end(), byAvgTemp);

        for (const auto& season: seasons) {
            std::cout << season.name << '\n';
        }
        std::cout << std::endl;

        return EXIT_SUCCESS;
    };
}
