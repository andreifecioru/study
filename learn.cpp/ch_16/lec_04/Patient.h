#pragma once

#include "Printable.h"

#include <iostream>
#include <functional>
#include <vector>
#include <utility>


namespace ch_16_lecture_04 {
    class Doctor;

    class Patient
            : public Printable {

    public:
        Patient(std::string name)
         : m_name {std::move(name)} {}

        void addDoctor(Doctor &doctor);

        [[nodiscard]] std::string_view getName() const { return m_name; }

    protected:
        void print(std::ostream &os) const override;

    private:
        std::string m_name {};
        std::vector<std::reference_wrapper<Doctor>> m_doctors {};
    };

}



