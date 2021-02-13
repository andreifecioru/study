#pragma once

namespace ch_08_lecture_05_quiz_01 {
    struct AdRevenueTracker {
        int adCount{};
        double adClickedRatio{};
        double avgRevenuePerClick{};
    };

    int run();
}