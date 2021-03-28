#pragma once

#include "Printable.h"

#include <vector>


namespace ch_11_lecture_xx_quiz_04 {
    class Card;

    class Hand
        : public Printable {

    public:
        Hand& operator+=(const Card& card);
        [[nodiscard]] int getValue() const;
        void show(std::string_view prompt="") const;
        void dropAllCards();

    protected:
        void print(std::ostream& os) const override;

    private:
        std::vector<Card> m_cards;
    };

}



