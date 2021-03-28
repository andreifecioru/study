#pragma once

#include "Printable.h"
#include "Card.h"

#include <array>


namespace ch_11_lecture_xx_quiz_04 {
    class Deck
        : public Printable {
    public:
        static Deck create();

        Card& operator[](int index);
        void shuffle();

    protected:
        void print(std::ostream& os) const override;

    private:
        static int const s_numCardsInDeck { 52 };
        std::array<Card, s_numCardsInDeck> m_cards;

        static unsigned s_rndSeed;

    };
}



