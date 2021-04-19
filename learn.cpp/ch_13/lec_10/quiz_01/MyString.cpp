#include "MyString.h"

#include <iostream>
#include <string>
#include <cmath>

namespace ch_13_lecture_10_quiz_01 {
    void MyString::print(std::ostream &os) const {
        os << m_content;
    }

    MyString MyString::operator()(int start_idx, int length) const {
        std::string result {};

        auto stop_idx = std::min(m_content.size(), static_cast<std::string::size_type>(start_idx + length));
        for (auto i { start_idx }; i < stop_idx; ++i) {
            result += m_content.at(i);
        }

        return result;
    }
}
