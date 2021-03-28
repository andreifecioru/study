#include "Monster.h"

#include <iostream>
#include <string>

namespace ch_11_lecture_xx_quiz_03 {
    std::string_view Monster::monsterTypeToString(const Type type) {
        return Monster::s_monsterTypeStrings[type];
    }

    void Monster::print(std::ostream &os) const {
        os << m_name << " the " << monsterTypeToString(m_type)
           << " has " << m_hp << " and says " << m_roar << '.';
    }
}
