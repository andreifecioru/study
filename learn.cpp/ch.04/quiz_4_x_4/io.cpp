#include "io.hpp"

#include <iostream>

using namespace std;

double getTowerHight() {
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
