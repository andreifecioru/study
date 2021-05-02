#pragma once

#include "Printable.h"
#include "Patient.h"

#include <iostream>
#include <utility>
#include <vector>


namespace ch_16_lecture_04 {
    class Doctor
        : public Printable {

    public:
        Doctor(std::string name)
        : m_name { std::move(name) } {}

        [[nodiscard]] std::string_view getName() const { return m_name; }

    protected:
        void print(std::ostream &os) const override;

    private:
        std::string m_name {};
        std::vector<std::reference_wrapper<const Patient>> m_patients;

        void addPatient(const Patient& patient);

        friend void Patient::addDoctor(Doctor &doctor);
    };

}



