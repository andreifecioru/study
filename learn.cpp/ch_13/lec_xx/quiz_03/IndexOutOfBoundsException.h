#pragma once

#define FMT_HEADER_ONLY
#include <fmt/format.h>

#include <exception>
#include <string>


namespace ch_13_lecture_xx_quiz_03 {
    class IndexOutOfBoundsException
        : public std::exception {
    public:
        IndexOutOfBoundsException(int capacity, int index)
            : m_msg { fmt::format("Index out of bounds. Capacity: {}. Index: {}", capacity, index) } {}

        ~IndexOutOfBoundsException() override = default;

        [[nodiscard]] const char * what() const noexcept override {
            return m_msg.data();
        }

    private:
        std::string m_msg {};
    };
}