#pragma once

#include <array>
#include <vector>


namespace ch_09_lecture_xx_quiz_07 {
    constexpr int maxScore{21};
    constexpr int minDealerScore{17};

    enum class Rank {
        _2, _3, _4, _5, _6, _7, _8, _9, _10, jack, queen, king, ace,
        max_rank
    };

    enum class Suit {
        club, diamond, heart, spade,
        max_suit
    };

    enum class GameResult {
        win, lose, tie, in_progress
    };

    struct Card {
        Rank rank{};
        Suit suit{};
    };

    constexpr int max_suit { static_cast<int>(Suit::max_suit) };
    constexpr int max_rank { static_cast<int>(Rank::max_rank) };

    using Deck = std::array<Card, 52>;
    using Hand = std::vector<Card>;

    int run();
}