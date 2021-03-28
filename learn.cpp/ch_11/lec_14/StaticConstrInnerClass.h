#pragma once

#include <array>


namespace ch_11_lecture_14 {
    class StaticConstrInnerClass {

    public:
        using values_size_t = int;
        static constexpr values_size_t s_capacity { 10 };
        using values_t = std::array<values_size_t, s_capacity>;

    private:
        static values_t s_values;

        class static_init {
        public:
            static_init();
        };
        static static_init static_initializer;

    public:
        [[nodiscard]] static const values_t& getValues() { return s_values; };
    };
}



