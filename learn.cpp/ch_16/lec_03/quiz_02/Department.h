#pragma once

#include "Printable.h"
#include "Teacher.h"

#include <iostream>
#include <vector>
#include <functional>


namespace ch_16_lecture_03_quiz_02 {
    class Department
        : public Printable {

    public:
        void add(const Teacher& teacher);

    protected:
        void print(std::ostream &os) const override;

    private:
        std::vector<std::reference_wrapper<const Teacher>> m_teachers {};
    };
}



