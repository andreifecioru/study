#pragma once

#include "Printable.h"

#include <iostream>
#include <string>
#include <utility>


namespace ch_16_lecture_03_quiz_02 {
    class Teacher
        : public Printable {

    public:
        Teacher(std::string name)
            : m_name(std::move(name)) {}

    protected:
        void print(std::ostream &os) const override;

    private:
        std::string m_name;
    };

}



