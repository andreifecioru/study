#include "quiz_02.h"

#include <iostream>


namespace ch_05_lecture_03_quiz_02 {
    using namespace std;

    namespace {
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
    }

    int run() {
        int num{ getInteger() };
        showResult(num, num % 2 == 1);

        return EXIT_SUCCESS;
    }
}
