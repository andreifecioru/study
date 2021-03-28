#include "Deck.h"
#include "Card.h"

#include <algorithm>
#include <random>
#include <chrono>


namespace ch_11_lecture_xx_quiz_04 {
    void Deck::print(std::ostream &os) const {
        std::for_each(m_cards.begin(), m_cards.end(), [&os](const Card& card){
            os << card << ' ';
        });
    }

    Card& Deck::operator[](int index) {
        return m_cards[index];
    }

    void Deck::shuffle() {
        std::shuffle(m_cards.begin(), m_cards.end(), std::default_random_engine(s_rndSeed));
    }

    Deck Deck::create() {
        Deck deck;
        for (auto s { 0 }; s < Card::max_suit; ++s) {
            for (auto  r { 0 }; r < Card::max_rank; ++r) {
                deck[s * Card::max_rank + r] = { static_cast<Card::Rank>(r), static_cast<Card::Suit>(s) };
            }
        }
        return deck;
    }

    unsigned Deck::s_rndSeed {[]() {
        return static_cast<unsigned>(std::chrono::system_clock::now().time_since_epoch().count());
    }()};
}
