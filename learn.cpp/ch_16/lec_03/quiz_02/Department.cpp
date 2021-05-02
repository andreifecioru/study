#include "Department.h"
#include "Teacher.h"

#include <iostream>
#include <algorithm>

namespace ch_16_lecture_03_quiz_02 {
    void Department::print(std::ostream &os) const {
        os << "Department (" << m_teachers.size() << " teachers)\n";
        std::for_each(m_teachers.begin(), m_teachers.end(), [&os](const auto teacher_w) {
            os << "\t - " << teacher_w.get() << '\n';
        });
    }

    void Department::add(const Teacher &teacher) {
        m_teachers.emplace_back(teacher);
    }

}