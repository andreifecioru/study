#include "IntArray.h"
#include "IndexOutOfBoundsException.h"

#include <cstring>


namespace ch_13_lecture_xx_quiz_03 {
    IntArray::IntArray(int capacity)
        : m_capacity { capacity } {
        if (m_capacity > 0) {
            m_data = new int[m_capacity];
            std::memset(m_data, 0, sizeof(int) * m_capacity);
        } else {
            m_capacity = 0;
            m_data = nullptr;
        }
    }

    IntArray::IntArray(const IntArray &other) {
        copyFromOther(other);
    }

    void IntArray::print(std::ostream &os) const {
        os << "IntArray( ";
        if (m_data) {
            for (auto i { 0 }; i < m_capacity; ++i) {
                os << m_data[i] << ' ';
            }
        }
        os << ')';
    }

    int& IntArray::operator[](int index) {
        if (!m_data || index < 0 || index > m_capacity) {
            throw IndexOutOfBoundsException(m_capacity, index);
        }
        return m_data[index];
    }

    int IntArray::operator[](int index) const {
        if (!m_data || index < 0 || index > m_capacity) {
            throw IndexOutOfBoundsException(m_capacity, index);
        }
        return m_data[index];
    }

    IntArray& IntArray::operator=(const IntArray &other) {
        if (this == &other)
            return *this;
        copyFromOther(other);

        return *this;
    }

    void IntArray::copyFromOther(const IntArray &other) {
        delete[] m_data;
        m_capacity = other.m_capacity;
        if (m_capacity > 0) {
            m_data = new int[m_capacity];
            std::memcpy(m_data, other.m_data, sizeof(int) * m_capacity);
        } else {
            m_capacity = 0;
            m_data = nullptr;
        }
    }
}
