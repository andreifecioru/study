#pragma once

#include <string_view>

namespace ch_10_lecture_15_quiz_02 {
    struct Season {
        std::string_view name{};
        double avgTemp{};
    };

    int run();
}