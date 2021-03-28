#pragma once

#include <iostream>

namespace ch_11_lecture_03_quiz_02 {
    class Point3D {
        int m_x{0};
        int m_y{0};
        int m_z{0};

        friend std::ostream &operator<<(std::ostream &os, const Point3D &rhs);

    public:
        Point3D() = default;

        Point3D(int x, int y, int z)
                : m_x{x}, m_y{y}, m_z{z} {}

        void setValues(int x, int y, int z);

        bool operator==(const Point3D &other) const;
    };
}