#include "quiz_03.h"

#include <iostream>
#include <vector>
#include <string_view>
#include <random>
#include <sstream>
#include <algorithm>


namespace ch_10_lecture_16_quiz_03 {
    namespace {

        constexpr std::string_view ErrPromptForPositiveInteger { "Enter a positive integer number (greater than 0).\n" };

        void ignoreLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        int getUserInputAsInteger(std::string_view prompt,
                                  std::string_view errPrompt = ErrPromptForPositiveInteger) {
            std::cout << prompt;

            while (true) {
                int userInput{};
                std::cin >> userInput;
                ignoreLine();

                if (userInput <= 0) {
                    std::cout << errPrompt;
                    continue;
                }

                return userInput;
            }
        }

        int generateValues(std::vector<int>& values, const int startValue, const int numValues) {
            static std::random_device device;
            static std::mt19937 rng { static_cast<std::mt19937::result_type>(device()) };
            static std::uniform_int_distribution<int> distribution(2, 4);

            int multiplier { distribution(rng) };

            for (auto i{0}; i < numValues; ++i) {
                int newValue { (startValue + i) * (startValue + i) * multiplier };
                values.push_back(newValue);
            }

            return multiplier;
        }

        void printValues(const std::vector<int>& values) {
            std::cout << "[ ";
            for (const int value: values) {
                std::cout << value << " ";
            }
            std::cout << "]" << std::endl;
        }

        void evaluateGuess(std::vector<int>& values, const int guess, GuessResult& outResult) {
            const auto found { std::find(values.begin(), values.end(), guess) };

            if (found == values.end()) {
                const auto closestValue { std::find_if(values.begin(), values.end(), [guess](int value) { return abs(guess - value) <= 4; } ) };
                if (closestValue == values.end()) {
                    outResult = { GuessState::wrong, guess, NoClosestValue };
                } else {
                    outResult = { GuessState::close_enough, guess, *closestValue };
                }
            } else {
                values.erase(found);
                outResult = { GuessState::exact, guess, guess };
            }
        }

        void play() {
            const int startValue { getUserInputAsInteger("Start where? ") };
            const int numValues { getUserInputAsInteger("How many? ") };

            std::vector<int> values;
            int multiplier { generateValues(values, startValue, numValues) };
            printValues(values);

            std::cout << "I generated " << numValues << " square numbers. "
                      << "Do you know what each number is after multiplying it by " << multiplier << "?\n";

            GuessResult result;
            while (true) {
                const int guess { getUserInputAsInteger("> ") };
                evaluateGuess(values, guess, result);

                if (result.state == GuessState::exact && !values.empty()) {
                    std::cout << "Nice! " << values.size() << " numbers left." << std::endl;
                } else {
                    break;
                }
            }

            switch (result.state) {
                case GuessState::exact:
                    std::cout << "Nice! You found all numbers, good job!" << std::endl;
                    break;
                case GuessState::close_enough:
                    std::cout << result.guessValue << " is wrong! Try " << result.closestValue << " next time." << std::endl;
                    break;
                case GuessState::wrong:
                    std::cout << result.guessValue << " is wrong!" << std::endl;
                    break;
            }
        }
    }

    int run() {
        play();
        return EXIT_SUCCESS;
    }
}
