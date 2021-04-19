#pragma once

#include "Printable.h"

#include <iostream>
#include <string>
#include <utility>


namespace ch_13_lecture_10_quiz_01 {
    class MyString
        : public Printable {
    public:
        MyString(std::string content)
            : m_content { std::move(content) } {}

        MyString operator()(int start_idx, int length) const;

    protected:
        void print(std::ostream &os) const override;

    private:
        std::string m_content;
    };

}



