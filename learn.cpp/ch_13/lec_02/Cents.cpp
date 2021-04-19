#include "Cents.h"

namespace ch_13_lecture_02 {
    void Cents::print(std::ostream &os) const {
        os << "Cents(" << m_cents << ')';
    }

    Cents operator+(const Cents& c1, const Cents& c2) {
        return Cents(c1.m_cents + c2.m_cents);
    }
}
