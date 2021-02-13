#include "quiz_04.h"

#include <iostream>


namespace ch_04_lec_xx_quiz_04 {
    using namespace std;

    namespace {
        inline constexpr double gravity { 9.8 };

        double getTowerHeight() {
            cout << "Enter tower height in meters: ";

            double height{};
            cin >> height;

            return height;
        }

        void showBallHeight(int seconds, double height) {
            if (height > 0) {
                cout << "At " << seconds << " seconds the ball is at height: " << height << " meters." << endl;
            } else {
                cout << "At " << seconds << " seconds the ball is on the ground." << endl;
            }
        }

        double computeBallHeight(int seconds, double tower_height) {
            double distance_fallen = gravity * seconds * seconds / 2.0;

            return tower_height - distance_fallen;
        }
    }

    int run() {
        double tower_height{getTowerHeight()};
        double ball_height{};

        for (int i = 0; i <= 5; i++) {
            ball_height = computeBallHeight(i, tower_height);

            showBallHeight(i, ball_height);

            if (ball_height <= 0) break;
        }

        return EXIT_SUCCESS;
    }
}
