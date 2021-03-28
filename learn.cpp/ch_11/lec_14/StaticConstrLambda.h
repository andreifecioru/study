#pragma once

#include <array>


namespace ch_11_lecture_14 {
    class StaticConstrLambda {

    public:
        using values_size_t = int;
        static constexpr values_size_t s_capacity { 10 };
        using values_t = std::array<values_size_t, s_capacity>;

    private:
        static values_t s_values;

    public:
        [[nodiscard]] static const values_t& getValues() { return s_values; };
    };
}



