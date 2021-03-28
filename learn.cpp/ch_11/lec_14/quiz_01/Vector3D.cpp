#include "Vector3D.h"

namespace ch_11_lecture_14_quiz_01 {
    void Vector3D::print(std::ostream &os) const {
        os << "V<" << m_x << ", "
                   << m_y << ", "
                   << m_z << ">";
    }
}
