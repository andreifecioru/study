#include "add.hpp"
#include "io.hpp"

#include <cstdlib>
#include <iostream>

using namespace std;


int main() {

    int32_t op1{ getInteger() };
    int32_t op2{ getInteger() };

    showResult(op1 + op2);

    return EXIT_SUCCESS;
}
