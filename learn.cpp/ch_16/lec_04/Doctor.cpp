#include "Doctor.h"
#include "Patient.h"

#include <iostream>
#include <algorithm>


namespace ch_16_lecture_04 {
    void Doctor::print(std::ostream& os) const {
        os << "Doc. " << m_name
           << " (" << m_patients.size() << " patients)\n";

        std::for_each(m_patients.begin(), m_patients.end(), [&os](const auto& patient_w) {
            os << "  - " << patient_w.get().getName() << '\n';
        });
    }

    void Doctor::addPatient(const Patient& patient) {
        m_patients.emplace_back(patient);
    }

}
