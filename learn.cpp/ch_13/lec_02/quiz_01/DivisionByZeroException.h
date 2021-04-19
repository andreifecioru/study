#pragma once

#include <exception>

namespace ch_13_lecture_02_quiz_01 {
    class DivisionByZeroException
        : public std::exception {

    public:
        DivisionByZeroException() noexcept = default;
        ~DivisionByZeroException() override = default;

        [[nodiscard]] const char * what() const noexcept override {
            return "Cannot divide by 0.";
        }
    };
}




