#pragma once

#include "Printable.h"
#include "Hand.h"

#include <iostream>
#include <array>
#include <string>


namespace ch_11_lecture_xx_quiz_04 {
    class Card
        : public Printable {

    public:
        enum Rank {
            _2, _3, _4, _5, _6, _7, _8, _9, _10, jack, queen, king, ace,
            max_rank
        };

        enum Suit {
            club, diamond, heart, spade,
            max_suit
        };

        Card(Rank rank, Suit suit)
            : m_rank { rank }
            , m_suit { suit } {}

        Card() = default;

    protected:
        void print(std::ostream& os) const override;

    private:
        Rank m_rank { _2 };
        Suit m_suit { club };

        static constexpr std::array<std::string_view, max_rank> s_rankStrings {
            "2", "3", "4", "5", "6",
            "7", "8", "9", "T", "J", "Q",
            "K", "A"
        };

        static constexpr std::array<std::string_view, max_suit> s_suitStrings {
            "C", "D", "H", "S"
        };

        [[nodiscard]] std::string toString() const;
        [[nodiscard]] int getValue(bool aceIsOne=false) const;

        friend int Hand::getValue() const;
    };
}



