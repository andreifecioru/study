#pragma once

#include <array>
#include <iostream>
#include <exception>


namespace ch_11_lecture_11_quiz_03 {
    class Stack {
        static constexpr int MaxCapacity { 10 };

        using container_type = std::array<int, MaxCapacity>;
        using size_type = container_type::size_type;

        container_type m_data {};
        size_type m_nextIdx {0 };

        friend std::ostream& operator<<(std::ostream& os, const Stack& rhs);

    public:
        Stack() = default;

        void push(int value);
        int pop();
        void reset() noexcept;


        class OpFailedException
            : public std::exception {

            std::string m_message;

        public:
            explicit OpFailedException(std::string message)
                : m_message { std::move(message) } {};
            ~OpFailedException() override = default;

            [[nodiscard]] const char* what() const noexcept override {
                return m_message.c_str();
            }
        };
    };
}



