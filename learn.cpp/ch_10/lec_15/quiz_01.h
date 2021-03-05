#pragma once

#include <string_view>

namespace ch_10_lecture_15_quiz_01 {
    struct Student {
        std::string_view name{};
        int points;
    };

    int run();
}