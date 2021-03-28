#pragma once

#include "Printable.h"

// needed by the friend declaration of `move`
// NOTE: for friend declaration we need complete types.
#include "Point3D.h"

#include <iostream>


namespace ch_11_lecture_14_quiz_01 {
    class Vector3D
        : public Printable {

        double m_x { 0.0 };
        double m_y { 0.0 };
        double m_z { 0.0 };

        friend void Point3D::move(const Vector3D& delta);

    protected:
        void print(std::ostream &os) const override;

    public:
        Vector3D(double x, double y, double z)
            : m_x { x }
            , m_y { y }
            , m_z { z } {}

    };
}



