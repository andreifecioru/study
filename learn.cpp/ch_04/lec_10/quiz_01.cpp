#include "quiz_01.h"

#include <iostream>


namespace ch_04_lecture_10_quiz_01 {
    using namespace std;

    bool isPrime(int num);

    int run() {
        cout << "Enter an integer: ";

        int num{};
        cin >> num;

        cout << std::boolalpha; // print booleans as "true" and "false"
        cout << "Is " << num << " prime? " << isPrime(num) << endl;

        return EXIT_SUCCESS;
    }

    bool isPrime(int num) {
        if (num <= 3) return true;

        for (int i = 2; i < num / 2; i++) {
            if (num % i == 0) return false;
        }

        return true;
    }
}
