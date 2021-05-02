#include "Patient.h"
#include "Doctor.h"

#include <iostream>
#include <algorithm>


namespace ch_16_lecture_04 {
    void Patient::print(std::ostream &os) const {
        os << m_name
           << " (" << m_doctors.size() << " doctors)\n";

        std::for_each(m_doctors.begin(), m_doctors.end(), [&os](const auto& doctor_w) {
            os << "  - Doc. " << doctor_w.get().getName() << '\n';
        });
    }

    void Patient::addDoctor(Doctor &doctor) {
        m_doctors.emplace_back(doctor);
        doctor.addPatient(*this);
    }
}
