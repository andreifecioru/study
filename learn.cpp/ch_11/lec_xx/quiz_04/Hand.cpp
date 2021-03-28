#include "Hand.h"
#include "Card.h"
#include "Game.h"

#include <algorithm>


namespace ch_11_lecture_xx_quiz_04 {
    void Hand::print(std::ostream &os) const {
        std::for_each(m_cards.begin(), m_cards.end(), [&os](const Card& card) {
            os << card << ' ';
        });

        os << " (value: " << getValue() << ')' << std::endl;
    }

    void Hand::show(std::string_view prompt) const {
        std::cout << prompt << *this;
    }

    void Hand::dropAllCards() {
        m_cards.clear();
    }

    Hand& Hand::operator+=(const Card &card) {
        m_cards.push_back(card);
        return *this;
    }

    int Hand::getValue() const {
        int total{0};

        for (auto& card: m_cards) {
            if (card.m_rank == Card::ace) {
                int temp_total = total + card.getValue();
                total = temp_total ? temp_total < Game::s_maxScore : total + card.getValue(true);
            } else {
                total += card.getValue();
            }
        }

        return total;
    }
}
