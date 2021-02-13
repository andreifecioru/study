#include "quiz_06.h"

#include <iostream>
#include <cmath>


namespace opt_01_lecture_04_quiz_06 {
    using namespace std;

    namespace {
        uint_fast8_t getInteger() {
            uint_fast16_t num{};
            while (true) {
                cout << "Enter integer value in interval [0, 255): ";
                cin >> num;

                if (num > 255)
                    cout << "Value must be a positive integer less than 256.\n";
                else
                    break;
            }

            return static_cast<uint_fast8_t>(num);
        }

        void showResult(char *buff) {
            cout << "Result: ";
            for (size_t i = 0; i < 8; i++) {
                cout << buff[i];
            }
            cout << endl;
        }

        int compare(int num, int idx, char *buff) {
            int threshold{ static_cast<int>(pow(2, idx)) };
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
    }

    int run() {
        uint_fast8_t num{getInteger()};

        char buff[8];
        convert(num, buff);
        showResult(buff);

        return EXIT_SUCCESS;
    }
}
