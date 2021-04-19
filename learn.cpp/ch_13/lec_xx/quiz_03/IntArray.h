#pragma once

#include "Printable.h"

namespace ch_13_lecture_xx_quiz_03 {
    class IntArray
        : public Printable {

    public:
        IntArray()
            : IntArray(s_defaultCapacity) {};
        IntArray(int capacity);
        IntArray(const IntArray& other);

        ~IntArray() {
            delete[] m_data;
        }

        int& operator[](int index);
        int operator[](int index) const;
        IntArray& operator=(const IntArray& other);

    protected:
        void print(std::ostream &os) const override;

    private:
        constexpr static int s_defaultCapacity { 100 };

        int m_capacity { s_defaultCapacity };
        int* m_data { nullptr };

        void copyFromOther(const IntArray& other);
    };
}



