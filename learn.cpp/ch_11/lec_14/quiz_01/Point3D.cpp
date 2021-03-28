#include "Point3D.h"

// needed by the implementation of `move`
// NOTE: at this point we need a complete type
#include "Vector3D.h"


namespace ch_11_lecture_14_quiz_01 {
    void Point3D::print(std::ostream &os) const {
        os << "P<" << m_x << ", "
                   << m_y << ", "
                   << m_z << '>';
    }

    void Point3D::move(const Vector3D& delta) {
        m_x += delta.m_x;
        m_y += delta.m_y;
        m_z += delta.m_z;
    }
}