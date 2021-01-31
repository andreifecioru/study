#include "io.hpp"

#include <iostream>

using namespace std;

int getSmallInteger() {
    cout << "Enter a small integer value: ";

    int num{};
    cin >> num;

    return num;
}

int getLargeInteger() {
    cout << "Enter a large integer value: ";

    int num{};
    cin >> num;

    return num;
}

void showResults(int small, int large) {
    cout << "The smaller value is: " << small << endl;
    cout << "The larger value is: " << large << endl;
}
