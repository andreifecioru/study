#include "quiz_05.h"

#include <iostream>

namespace ch_02_lecture_03_quiz_05 {
    using namespace std;

    int32_t getInput();
    int32_t doubleNumber(int32_t num);

    int run() {
        int32_t num{getInput()};

        cout << "Result is: " << doubleNumber(num) << endl;

        return EXIT_SUCCESS;
    }

    int32_t getInput() {
        cout << "Enter integer value: ";

        int32_t num{};
        cin >> num;

        return num;
    }

    int32_t doubleNumber(int32_t num) {
        return num * 2;
    }
}