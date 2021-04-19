#include "Fraction.h"

#include <iostream>
#include <cmath>

namespace ch_13_lecture_02_quiz_01 {
    namespace {
        int gcd(int v1, int v2) {
            if (v1 == v2)
                return v1;
            else
                return gcd(std::abs(v1 - v2), std::min(v1, v2));
        }
    }


    Fraction::Fraction(int num, int denom)
            : m_num { num }
            , m_denom {denom} {
        if (m_denom == 0) {
            throw DivisionByZeroException();
        }
        simplify();
    }

    void Fraction::print(std::ostream &os) const {
        bool is_negative = m_num * m_denom < 0;
        os << (is_negative ? "-" : "") << std::abs(m_num);
        if (m_denom != 1) {
            os << '/' << std::abs(m_denom);
        }
    }

    void Fraction::simplify() {
        int gcd_val = gcd(std::abs(m_num), std::abs(m_denom));
        m_num /= gcd_val;
        m_denom /= gcd_val;
    }

    Fraction operator*(const Fraction& f1, const Fraction& f2) {
        return { f1.m_num * f2.m_num, f1.m_denom * f2.m_denom };
    }

    Fraction operator*(const Fraction& f1, int value) {
        return { f1.m_num * value, f1.m_denom };
    }

    Fraction operator*(int value, const Fraction& f1) {
        return { f1.m_num * value, f1.m_denom };
    }

}
