#include "Ball.h"

#include <iostream>


namespace ch_11_lecture_05_quiz_01 {
    std::ostream& operator<<(std::ostream& os, const Ball& ball) {
        os << "color: " << Ball::color2Str(ball.m_color)
           << ", radius " << ball.m_radius;
        return os;
    }

    std::string_view Ball::color2Str(const Color color) {
        switch (color) {
            case Color::black: return s_black;
            case Color::blue:  return s_blue;
            case Color::red:   return s_red;
            case Color::yellow:   return s_yellow;
            case Color::orange:   return s_orange;
            case Color::green:   return s_green;
            case Color::white:   return s_white;
            default:
                throw std::domain_error("Unsupported color.");
        }
    }
}
