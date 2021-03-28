#pragma once

#include "Printable.h"

#include <iostream>


namespace ch_11_lecture_xx_quiz_01 {
    class Point2D
        : public Printable {

    public:
        Point2D() = default;
        Point2D(double x, double y)
            : m_x { x }
            , m_y { y } {}

        [[nodiscard]] double distanceTo(const Point2D& other) const;
        friend double distanceBetween(const Point2D& p1, const Point2D& p2);

    protected:
        void print(std::ostream& os) const override;

    private:
        double m_x { 0.0 };
        double m_y { 0.0 };
    };
}



