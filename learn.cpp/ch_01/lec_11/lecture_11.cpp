#include "lecture_11.h"

#include <iostream>

namespace ch_01_lecture_11 {
    using namespace std;

    int run() {
        cout << "Enter an integer: ";
        int32_t num{};
        cin >> num;

        int32_t result_double{num * 2};
        cout << "Double " << num << " is: " << result_double << endl;

        int32_t result_triple{num * 3};
        cout << "Triple " << num << " is: " << result_triple << endl;

        return EXIT_SUCCESS;
    }
}
