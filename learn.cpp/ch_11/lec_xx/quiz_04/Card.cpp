#include "Card.h"

#include <string>


namespace ch_11_lecture_xx_quiz_04 {
    std::string Card::toString() const {
        return std::string(Card::s_rankStrings[m_rank]) +
               std::string(Card::s_suitStrings[m_suit]);
    }

    void Card::print(std::ostream &os) const {
        os << toString();
    }

    int Card::getValue(const bool aceIsOne) const {
        switch (m_rank) {
            case _2: return 2;
            case _3: return 3;
            case _4: return 4;
            case _5: return 5;
            case _6: return 6;
            case _7: return 7;
            case _8: return 8;
            case _9: return 9;
            case _10:
            case jack:
            case queen:
            case king:
                return 10;
            case ace:
                return aceIsOne ? 11 : 1;
            default:
                throw std::domain_error("Invalid card rank.");
        }
    }
}