#include "quiz_01.h"

#include <iostream>


namespace ch_08_lecture_05_quiz_01 {
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

        AdRevenueTracker getTrackingData() {
            int adCount { getUserInput<int>("How many ads? ") };
            double clickRatio { getUserInput<double>("What percentage of ads ere clicked? ")};
            double revenuePerClick { getUserInput<double>("How much $ per click? ")};

            return {adCount, clickRatio, revenuePerClick};
        }

        double computeTotalRevenue(const AdRevenueTracker& data) {
            return data.adCount * data.avgRevenuePerClick * data.adClickedRatio;
        }

        void printAdRevenueStats(const AdRevenueTracker& data) {
            std::cout << "-----[ Ad revenue stats ]---------\n"
                      << "Number of ads displayed: " << data.adCount << '\n'
                      << "Percentage of clicked ads: " << data.adClickedRatio << '\n'
                      << "Revenue per ad clicked: " << data.avgRevenuePerClick << '\n'
                      << "----------------------------------\n\n"
                      << "TOTAL: $" << computeTotalRevenue(data);
        }
    }

    int run() {
        AdRevenueTracker adData { getTrackingData() };
        printAdRevenueStats(adData);

        return EXIT_SUCCESS;
    }
}
