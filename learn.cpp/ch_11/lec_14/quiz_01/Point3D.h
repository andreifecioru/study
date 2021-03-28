#pragma once

#include "Printable.h"

#include <iostream>


namespace ch_11_lecture_14_quiz_01 {
    // this fwd. declaration is needed by the `move` prototype
    // NOTE: we don't need a complete type here
    class Vector3D;

    class Point3D
        : public Printable {

        double m_x { 0.0 };
        double m_y { 0.0 };
        double m_z { 0.0 };

    protected:
        void print(std::ostream& os) const override;

    public:
        Point3D(double x, double y, double z)
            : m_x { x }
            , m_y { y}
            , m_z { z } {}

        void move(const Vector3D& delta);
    };
}



