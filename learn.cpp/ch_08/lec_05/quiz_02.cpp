#include "quiz_02.h"

#include <iostream>

namespace ch_08_lecture_05_quiz_02 {
    namespace {
        void ignoreLine() {
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }

        template <class T>
        T getUserInput(const std::string& prompt) {
            std::cout << prompt;

            T userInput{};
            std::cin >> userInput;
            ignoreLine();

            return userInput;
        }

        Fraction getFraction(const std::string& prompt) {
            std::cout << prompt;
            return {
                getUserInput<int>("Enter the numerator: "),
              getUserInput<int>("Enter value for the denominator: ")
            };
        }

        int gcd(int a, int b) {
            if (a > b) return gcd(a - b, b);
            else if (b > a) return gcd(a, b - a);
            else return a;
        }

        Fraction simplify(const Fraction& f) {
            int simplificationFactor { gcd(f.num, f.denom) };
            // std::cout << "Applying simplification factor: " << simplificationFactor << std::endl;
            return {
                f.num / simplificationFactor,
                f.denom / simplificationFactor
            };
        }

        Fraction add(const Fraction& op1, const Fraction& op2) {
            Fraction result{};
            result.num = op1.num * op2.denom + op2.num * op1.denom;
            result.denom = op1.denom * op2.denom;

            return simplify(result);
        }

        Fraction mul(const Fraction& op1, const Fraction& op2) {
            Fraction result{
                op1.num * op2.num,
                op1.denom * op2.denom
            };

            return simplify(result);
        }

        std::string showFraction(const Fraction& f) {
            return std::to_string(f.num) + '/' + std::to_string(f.denom);
        }
    }

    int run() {
        Fraction f1 { getFraction("Enter values for the 1st fraction: \n") };
        Fraction f2 { getFraction("Enter values for the 2nd fraction: \n") };

        std::cout << showFraction(f1) << " + " << showFraction(f2) << " = "
                  << showFraction(add(f1, f2)) << std::endl;

        std::cout << showFraction(f1) << " * " << showFraction(f2) << " = "
                  << showFraction(mul(f1, f2)) << std::endl;

        return EXIT_SUCCESS;
    }
}
