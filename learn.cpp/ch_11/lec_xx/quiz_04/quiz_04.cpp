#include "quiz_04.h"
#include "Game.h"

#include <iostream>


namespace ch_11_lecture_xx_quiz_04 {
    namespace {}

    int run() {
        std::cout << "Welcome to a nice game of Blackjack!" << std::endl;

        Game game {};
        Game::Result gameResult { game.play() };

        switch (gameResult) {
            case Game::win:
                std::cout << "You win!" << std::endl;
                break;
            case Game::lose:
                std::cout << "You lose!" << std::endl;
                break;
            case Game::tie:
                std::cout << "It's a tie!" << std::endl;
                break;
            default:
                throw std::domain_error("Invalid game result.");
        }

        return EXIT_SUCCESS;
    }
}
