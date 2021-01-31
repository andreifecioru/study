#include <cstdlib>
#include <iostream>

#include "io.hpp"

using namespace std;

int main() {
    int small{getSmallInteger()};
    int large{getLargeInteger()};

    if (small > large) {
        cout << "Swapping values. \n";
        int temp {small};
        small = large;
        large = temp;

    }

    showResults(small, large);

    return EXIT_SUCCESS;
}
