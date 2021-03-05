#pragma once

namespace ch_10_lecture_16_quiz_03 {
    constexpr int NoClosestValue {-1};

    enum class GuessState { exact, close_enough, wrong };

    struct GuessResult {
        GuessState state {GuessState::exact};
        int guessValue{};
        int closestValue{};
    };

    int run();
}