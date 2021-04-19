#pragma once

#include "Printable.h"

#include <iostream>
#include <vector>

namespace ch_13_lecture_09_quiz_01 {
    class StudentGrades
        : public Printable {

    public:
        char& operator[](const std::string& name);
        char operator[](const std::string& name) const;

    protected:
        void print(std::ostream &os) const override;

    private:
        struct StudentGrade {
            std::string name {};
            char grade { 'A' };
        };

        std::vector<StudentGrade> m_grades {};
    };

}



