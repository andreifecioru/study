#include "quiz_01.h"

#include <iostream>

namespace ch_06_lecture_03_quiz_01 {
    using namespace std;

    namespace {
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
    }

    int run() {
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
}
