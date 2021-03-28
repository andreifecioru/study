#include "Game.h"

#include <iostream>

namespace ch_11_lecture_xx_quiz_04 {
    void Game::ignoreLine() {
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    }

    bool Game::hitOrStand() {
        do {
            std::cout << "\nHit or stand? [h/s] ";

            char userInput;
            std::cin >> userInput;
            ignoreLine();

            switch (userInput) {
                case 'h': return true;
                case 's': return false;
                default:
                    std::cout << "Invalid user input." << std::endl;
                    continue;
            }
        } while (true);
    }

    void Game::resetState() {
        m_deck.shuffle();
        m_cardIdx = 0;
        m_playerHand.dropAllCards();
        m_dealerHand.dropAllCards();
    }

    void Game::updateTable() const {
        std::cout << "\n--------------------------\n";
        m_dealerHand.show("Dealer's hand: ");
        m_playerHand.show("Your hand: ");
    }

    Game::Result Game::checkGameScore(const bool bothPlayersStand) const {
        int playerHandValue { m_playerHand.getValue() };
        int dealerHandValue { m_dealerHand.getValue() };

        // Check game-ending conditions.
        if (playerHandValue > Game::s_maxScore) {
            return Result::lose;
        }

        if (dealerHandValue > Game::s_maxScore) {
            return Result::win;
        }

        if (bothPlayersStand) {
            if (playerHandValue > dealerHandValue)
                return Result::win;
            if (playerHandValue < dealerHandValue)
                return Result::lose;
            return Result::tie;
        }

        return Result::in_progress;
    }

    const Card& Game::deal() {
        return m_deck[m_cardIdx++];
    }

    Game::Result Game::play() {
        resetState();

        // Dealer gets one card and the player two.
        // NOTE: the dealer gets his 2snd card after the table is updated
        m_dealerHand += deal();
        m_playerHand += deal();
        m_playerHand += deal();

        // Show the cards on table
        updateTable();

        // Dealer gets his 2nd card
        m_dealerHand += deal();

        // Main loop.
        while (true) {
            Result gameResult { checkGameScore(false) };

            if (gameResult != in_progress)
                return gameResult;

            bool playerHitsAgain { hitOrStand() };
            if (playerHitsAgain) { // player wants another card
                m_playerHand += deal();
            }

            bool dealerHitsAgain { m_dealerHand.getValue() < s_minDealerScore };
            if (dealerHitsAgain) { // dealer wants another card
                m_dealerHand += deal();
            }

            updateTable();

            if (!dealerHitsAgain && !playerHitsAgain) {
                return checkGameScore(true);
            }
        }
    }
}
