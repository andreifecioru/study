#include "StudentGrades.h"
#include "StudentNotFoundException.h"

#include <iostream>
#include <algorithm>


namespace ch_13_lecture_09_quiz_01 {

    char& StudentGrades::operator[](const std::string& name) {
        auto result = std::find_if(m_grades.begin(), m_grades.end(), [name](const StudentGrade& grade) {
            return grade.name == name;
        });

        if (result == m_grades.end()) {
            m_grades.push_back({ name, 'A' });
            return m_grades.back().grade;
        } else {
            return result->grade;
        }
    }

    char StudentGrades::operator[](const std::string &name) const {
        auto result = std::find_if(m_grades.begin(), m_grades.end(), [name](const StudentGrade& grade) {
            return grade.name == name;
        });

        if (result == m_grades.end()) {
            throw StudentNotFoundException { name };
        } else {
            return result->grade;
        }
    }

    void StudentGrades::print(std::ostream &os) const {
        os << "{\n";
        std::for_each(m_grades.begin(), m_grades.end(), [&os](const StudentGrade& grade){
            os << "\t" << grade.name << " : " << grade.grade << '\n';
        });
        os << "}\n";
    }
}