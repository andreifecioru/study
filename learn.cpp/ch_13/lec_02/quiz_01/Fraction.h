#pragma once

#include "Printable.h"
#include "DivisionByZeroException.h"

#include <iostream>


namespace ch_13_lecture_02_quiz_01 {
    class Fraction
        : public Printable {

    public:
        Fraction(int num, int denom);

    protected:
        void print(std::ostream &os) const override;

    private:
        int m_num { 0 };
        int m_denom { 1 };

        void simplify();

        friend Fraction operator*(const Fraction& f1, const Fraction& f2);
        friend Fraction operator*(const Fraction& f1, int value);
        friend Fraction operator*(int value, const Fraction& f1);
    };
}




