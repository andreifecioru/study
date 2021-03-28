#pragma once

#include "Deck.h"
#include "Hand.h"

namespace ch_11_lecture_xx_quiz_04 {
    class Game {
    public:
        static const int s_maxScore { 21 };
        static const int s_minDealerScore { 17 };

        enum Result {
            win, lose, tie, in_progress
        };

        Result play();

    private:
        Deck m_deck { Deck::create() };
        Hand m_playerHand {};
        Hand m_dealerHand {};
        int m_cardIdx { 0 };

        void resetState();
        void updateTable() const;
        [[nodiscard]] Result checkGameScore(bool bothPlayersStand) const;
        const Card& deal();

        static void ignoreLine();
        static bool hitOrStand();
    };
}




