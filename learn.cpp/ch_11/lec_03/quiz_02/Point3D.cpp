#include "Point3D.h"

#include <iostream>


namespace ch_11_lecture_03_quiz_02 {
    void Point3D::setValues(const int x, const int y, const int z) {
        m_x = x;
        m_y = y;
        m_z = z;
    }

    bool Point3D::operator==(const Point3D &other) const {
        return m_x == other.m_x && m_y == other.m_y && m_z == other.m_z;
    }

    std::ostream &operator<<(std::ostream &os, const Point3D &rhs) {
        os << "<" << rhs.m_x << ", " << rhs.m_y << ", " << rhs.m_z << ">";
        return os;
    }
}