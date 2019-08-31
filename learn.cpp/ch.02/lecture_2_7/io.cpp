#include "io.hpp"

#include <iostream>

using namespace std;

int32_t getInteger() {
    cout << "Enter integer value: ";

    int32_t num{};
    cin >> num;

    return num;
}

void showResult(int32_t result) {
    cout << "Result is: " << result << endl;
}
