#include "Point2D.h"

#include <cmath>


namespace ch_11_lecture_xx_quiz_01 {
    void Point2D::print(std::ostream &os) const {
        os << "P<" << m_x << ", "
                   << m_y << '>';
    }

    double Point2D::distanceTo(const Point2D &other) const {
        const double x_distance = std::abs(m_x - other.m_x);
        const double y_distance = std::abs(m_y - other.m_y);
        return std::sqrt(x_distance * x_distance + y_distance * y_distance);
    }

    double distanceBetween(const Point2D& p1, const Point2D& p2) {
        return p1.distanceTo(p2);
    }
}
