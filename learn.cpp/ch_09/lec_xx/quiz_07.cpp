#include "quiz_07.h"

#include <iostream>
#include <algorithm>
#include <random>
#include <chrono>
#include <cassert>


namespace ch_09_lecture_xx_quiz_07 {
    namespace {
        std::string card2Str(const Card& card) {
            std::string result{};

            switch (card.rank) {
                case Rank::_2: result = "2"; break;
                case Rank::_3: result = "3"; break;
                case Rank::_4: result = "4"; break;
                case Rank::_5: result = "5"; break;
                case Rank::_6: result = "6"; break;
                case Rank::_7: result = "7"; break;
                case Rank::_8: result = "8"; break;
                case Rank::_9: result = "9"; break;
                case Rank::_10: result = "T"; break;
                case Rank::jack: result = "J"; break;
                case Rank::queen: result = "Q"; break;
                case Rank::king: result = "K"; break;
                case Rank::ace: result = "A"; break;
                default:
                    throw;
            }

            switch (card.suit) {
                case Suit::club: result += "C"; break;
                case Suit::diamond: result += "D"; break;
                case Suit::heart: result += "H"; break;
                case Suit::spade: result += "S"; break;
                default:
                    throw;
            }

            return result;
        }

        void buildDeck(Deck& deck) {
            for (auto s{0}; s < max_suit; ++s) {
                for (auto r{0}; r < max_rank; ++r) {
                    deck[s * max_rank + r] = { static_cast<Rank>(r), static_cast<Suit>(s) };
                }
            }
        }

        void printDeck(const Deck& deck) {
            std::for_each(deck.begin(), deck.end(), [](const Card& card){
                std::cout << card2Str(card) << ' ';
            });
            std::cout << std::endl;
        }

        int getCardValue(const Card& card, const bool aceIsOne=false) {
            switch (card.rank) {
                case Rank::_2: return 2;
                case Rank::_3: return 3;
                case Rank::_4: return 4;
                case Rank::_5: return 5;
                case Rank::_6: return 6;
                case Rank::_7: return 7;
                case Rank::_8: return 8;
                case Rank::_9: return 9;
                case Rank::_10:
                case Rank::jack:
                case Rank::queen:
                case Rank::king: return 10;
                case Rank::ace: return aceIsOne ? 11 : 1;
                default:
                    throw;
            }
        }

        void shuffleDeck(Deck& deck) {
            static unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
            std::shuffle(deck.begin(), deck.end(), std::default_random_engine(seed));
        }

        int getHandValue(const Hand& hand) {
            int total{0};

            for (auto& card: hand) {
                if (card.rank == Rank::ace) {
                    int temp_total = total + getCardValue(card);
                    total = temp_total ? temp_total < maxScore: total + getCardValue(card, true);
                } else {
                    total += getCardValue(card);
                }
            }

            return total;
        }

        void showHand(const Hand& hand, const std::string& prompt = "") {
            std::cout << prompt;
            for (auto& card: hand) {
                std::cout << card2Str(card) << ' ';
            }

            std::cout << " (value: " << getHandValue(hand) << ')' << std::endl;
        }

        void updateTable(Hand& dealerHand, Hand& playerHand) {
            std::cout << "\n--------------------------\n";
            showHand(dealerHand, "Dealer's hand: ");
            showHand(playerHand, "Your hand: ");
        }

        Card deal(const Deck& deck) {
            static int cardIdx{0};
            return deck.at(cardIdx++);
        }

        void ignoreLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        bool hitOrStand() {
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

        GameResult checkGameScore(const Hand& dealerHand,
                                  const Hand& playerHand,
                                  const bool bothPlayersStand) {
            int playerHandValue{getHandValue(playerHand)};
            int dealerHandValue{getHandValue(dealerHand)};

            // Check game-ending conditions.
            if (playerHandValue > maxScore) {
                return GameResult::lose;
            }

            if (dealerHandValue > maxScore) {
                return GameResult::win;
            }

            if (bothPlayersStand) {
                if (playerHandValue > dealerHandValue)
                    return GameResult::win;
                if (playerHandValue < dealerHandValue)
                    return GameResult::lose;
                return GameResult::tie;
            }

            return GameResult::in_progress;
        }


        GameResult playBlackjack() {
            // Build and shuffle the deck
            Deck deck{};
            buildDeck(deck);
            shuffleDeck(deck);

            Hand playerHand{};
            Hand dealerHand{};

            // Dealer gets one card and the player two.
            // NOTE: the dealer gets his 2snd card after the table is updated
            dealerHand.push_back(deal(deck));
            playerHand.push_back(deal(deck));
            playerHand.push_back(deal(deck));

            // Show the cards on table
            updateTable(dealerHand, playerHand);

            // Dealer gets his 2nd card
            dealerHand.push_back(deal(deck));

            // Main loop.
            while (true) {
                GameResult gameResult = checkGameScore(dealerHand, playerHand, false);

                if (gameResult != GameResult::in_progress)
                    return gameResult;

                bool playerHitsAgain{hitOrStand()};
                if (playerHitsAgain) { // player wants another card
                    playerHand.push_back(deal(deck));
                }

                bool dealerHitsAgain{getHandValue(dealerHand) < minDealerScore};
                if (dealerHitsAgain) { // dealer wants another card
                    dealerHand.push_back(deal(deck));
                }

                updateTable(dealerHand, playerHand);

                if (!dealerHitsAgain && !playerHitsAgain) {
                    return checkGameScore(dealerHand, playerHand, true);
                }
            }
        }

    }

    int run() {
        std::cout << "Welcome to a nice game of Blackjack!" << std::endl;

        GameResult gameResult{playBlackjack()};

        switch (gameResult) {
            case GameResult::win:
                std::cout << "You win!" << std::endl;
                break;
            case GameResult::lose:
                std::cout << "You lose!" << std::endl;
                break;
            case GameResult::tie:
                std::cout << "It's a tie!" << std::endl;
                break;
            default:
                assert(false);
        }

        return EXIT_SUCCESS;
    }
}
