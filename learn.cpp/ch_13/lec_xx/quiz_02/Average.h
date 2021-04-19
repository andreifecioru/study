#pragma once

#include "Printable.h"

#include <iostream>


namespace ch_13_lecture_xx_quiz_02 {
    class Average
        : public Printable {

    public:
        Average& operator+=(int value);

    protected:
        void print(std::ostream &os) const override;

    private:
        std::int_least32_t m_sum { 0 };
        std::int_least8_t m_count { 0 };

        double m_average { 0.0 };
    };
}



