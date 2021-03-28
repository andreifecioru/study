#include "quiz_01.h"
#include "Ball.h"

#include <iostream>

namespace ch_11_lecture_05_quiz_01 {
    namespace {}

    int run() {
        Ball def{};
        std::cout << def << std::endl;

        Ball blue { Ball::Color::blue };
        std::cout << blue << std::endl;

        Ball yellow { Ball::Color::yellow };
        std::cout << yellow << std::endl;

        Ball orange { Ball::Color::orange };
        std::cout << orange << std::endl;

        Ball red { Ball::Color::red };
        std::cout << red << std::endl;

        Ball green { Ball::Color::green };
        std::cout << green << std::endl;

        Ball white { Ball::Color::white, 30.0 };
        std::cout << white << std::endl;

        Ball black { Ball::Color::black, 1000.0};
        std::cout << black << std::endl;

        yellow = Ball { Ball::Color::yellow, 1000000.0 };
        std::cout << yellow << std::endl;

        return EXIT_SUCCESS;
    };
}
