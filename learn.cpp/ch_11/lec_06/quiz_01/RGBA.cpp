#include "RGBA.h"

#include <iostream>

namespace ch_11_lecture_06_quiz_01 {
    std::ostream& operator<<(std::ostream& os, const RGBA& obj) {
        os << "r=" << static_cast<int>(obj.m_red)
           << " g=" << static_cast<int>(obj.m_green)
           << " b=" << static_cast<int>(obj.m_blue)
           << " a=" << static_cast<int>(obj.m_alpha);
        return os;
    }
}
