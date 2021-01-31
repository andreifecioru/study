#include "io.hpp"

#include <iostream>

using namespace std;

uint_fast8_t getInteger() {
    uint_fast16_t num{};
    while (true) {
        cout << "Enter integer value in interval [0, 255): ";
        cin >> num;

        if (num > 255)
            cout << "Value must be a positive integer less than 256.\n";
        else
            break;
    }

    return static_cast<uint_fast8_t>(num);
}

void showResult(char *buff) {
    cout << "Result: ";
    for (size_t i = 0; i < 8; i++) {
        cout << buff[i];
    }
    cout << endl;
}