#include <cstdlib>
#include <iostream>

#include "constants.hpp"
#include "io.hpp"

using namespace std;

double computeBallHeight(int seconds, double tower_height);

int main() {
    double tower_height{getTowerHight()};
    double ball_height{};

    for (int i = 0; i <= 5; i++) {
        ball_height = computeBallHeight(i, tower_height);

        showBallHeight(i, ball_height);

        if (ball_height <= 0) break;
    }

    return EXIT_SUCCESS;
}

double computeBallHeight(int seconds, double tower_height) {
    double distance_fallen = constants::gravity * seconds * seconds / 2.0;

    return tower_height - distance_fallen;
}
