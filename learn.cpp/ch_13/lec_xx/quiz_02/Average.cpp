#include "Average.h"

#include <iostream>

namespace ch_13_lecture_xx_quiz_02 {
    void Average::print(std::ostream &os) const {
        os << m_average
           << " (sum: " << m_sum
           << ", count: " << static_cast<int>(m_count)
           << ')';
    }

    Average& Average::operator+=(int value) {
        m_sum += value;
        ++m_count;
        m_average = static_cast<double>(m_sum) / m_count;

        return *this;
    }
}
