#pragma once

#include "Printable.h"

namespace ch_13_lecture_02 {
    class MinMax
        : public Printable {

    public:
        MinMax(int min, int max)
            : m_min { min }
            , m_max { max } {};

    protected:
        void print(std::ostream &os) const override;

    private:
        int m_min { 0 };
        int m_max { 0 };

        friend MinMax operator+(const MinMax& mm, int value);
        friend MinMax operator+(int value, const MinMax& mm);
        friend MinMax operator+(const MinMax& mm1, const MinMax& mm2);
    };
}



