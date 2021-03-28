#include "Stack.h"

#include <iostream>
#include <algorithm>


namespace ch_11_lecture_11_quiz_03 {

    std::ostream& operator<<(std::ostream& os, const Stack& rhs) {
        os << "( ";

        std::for_each(rhs.m_data.begin(), &rhs.m_data[rhs.m_nextIdx], [&os](const int value) {
            os << value << " ";
        });

        os << ")";

        return os;
    }

    void Stack::push(int value) {
        if (m_nextIdx >= m_data.size()) {
            throw OpFailedException { "Stack reached it's max capacity." };
        }

        m_data[m_nextIdx++] = value;
    }

    int Stack::pop() {
        if (m_nextIdx <= 0) {
            throw OpFailedException { "Stack is empty." };
        }

        return m_data[--m_nextIdx];
    }

    void Stack::reset() noexcept {
        m_nextIdx = 0;
    }
}
