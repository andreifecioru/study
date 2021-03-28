#pragma once

#include <iostream>

namespace ch_11_lecture_05_quiz_01 {
    class Ball {

    public:
        enum class Color {
            black,
            blue,
            red,
            yellow,
            orange,
            green,
            white,
        };

        explicit Ball(Color color = Color::black, double radius = 10.0)
            : m_color { color }
            , m_radius { radius } {}

    private:
        static constexpr char s_black[] {"s_black" };
        static constexpr char s_blue[] {"s_blue" };
        static constexpr char s_red[] {"s_red" };
        static constexpr char s_yellow[] {"s_yellow" };
        static constexpr char s_orange[] {"s_orange" };
        static constexpr char s_green[] {"s_green" };
        static constexpr char s_white[] { "s_white" };

        Color m_color { Color::black };
        double m_radius;

        friend std::ostream& operator<<(std::ostream& os, const Ball& ball);

        static std::string_view color2Str(Color color);

    };

}



