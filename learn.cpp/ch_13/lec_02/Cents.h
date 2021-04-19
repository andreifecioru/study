#pragma once

#include "Printable.h"


namespace ch_13_lecture_02 {
    class Cents
        : public Printable {
    public:
        Cents(int cents)
            : m_cents { cents } {}

    protected:
        void print(std::ostream &os) const override;

    private:
        int m_cents { 0 };

        friend Cents operator+(const Cents& c1, const Cents& c2);
    };

}




