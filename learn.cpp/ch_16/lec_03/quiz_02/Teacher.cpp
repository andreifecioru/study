#include "Teacher.h"

#include <iostream>

namespace ch_16_lecture_03_quiz_02 {
    void Teacher::print(std::ostream &os) const {
        os << "Teacher(" << m_name << ')';
    }
}
