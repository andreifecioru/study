#include "io.hpp"

#include <iostream>

using namespace std;

int getInteger() {
    cout << "Enter integer value: ";

    int num{};
    cin >> num;

    return num;
}

void showResult(int num, bool is_odd) {
    if (is_odd) {
        cout << num << " is odd" << endl;
    } else {
        cout << num << " is even" << endl;
    }
}
