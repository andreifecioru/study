#include "MinMax.h"

namespace ch_13_lecture_02 {
    void MinMax::print(std::ostream &os) const {
        os << "(min: " << m_min
           << ", max: " << m_max << ')';
    }

    MinMax operator+(const MinMax& mm, int value) {
        int min_val { value < mm.m_min ? value : mm.m_min };
        int max_val { value > mm.m_max ? value : mm.m_max };

        return { min_val, max_val };
    }

    MinMax operator+(int value, const MinMax& mm) {
        return mm + value;
    }

    MinMax operator+(const MinMax& mm1, const MinMax& mm2) {
        return mm1 + mm2.m_min + mm2.m_max;
    }
}
