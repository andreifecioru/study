#include <cmath>
#include <cstdlib>
#include <iostream>

#include "io.hpp"

using namespace std;

int compare(int num, int idx, char *buff) {
    double threshold{pow(2, idx)};
    buff[7 - idx] = (num >= threshold) ? '1' : '0';
    return num >= threshold ? num - threshold : num;
}

void convert(uint_fast8_t num, char *buff) {
    int work_val{num};

    for (int i = 7; i >= 0; i--) {
        work_val = compare(work_val, i, buff);
    }

    cout << endl;
}

int main() {
    uint_fast8_t num{getInteger()};

    char buff[8];
    convert(num, buff);
    showResult(buff);

    return EXIT_SUCCESS;
}
