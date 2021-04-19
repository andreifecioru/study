#pragma once

#define FMT_HEADER_ONLY
#include <fmt/format.h>

#include <exception>
#include <utility>


namespace ch_13_lecture_09_quiz_01 {
    class StudentNotFoundException
        : public std::exception {

    public:
        explicit StudentNotFoundException(std::string name)
            : m_msg { fmt::format("Student not found with name: {}", name) } {};
        ~StudentNotFoundException() override = default;

        [[nodiscard]] const char * what() const noexcept override {
            return m_msg.data();
        }

    private:
        std::string m_msg {};
    };
}



