#include "io.hpp"

#include <cstdlib>
#include <iostream>

using namespace std;


int main() {

    int num{ getInteger() };
    showResult(num, num % 2 == 1);

    return EXIT_SUCCESS;
}
