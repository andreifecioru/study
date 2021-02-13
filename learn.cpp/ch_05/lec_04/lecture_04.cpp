#include "lecture_04.h"

#include <iostream>


namespace ch_05_lecture_04 {
    using namespace std;

    int add(int x, int y) {
        return x + y;
    }

    int run() {
        int num {4};

        // the result depends on the order the compiler evaluates the input args.
        // (which depends on the compiler implementation)

        // NOTE: this does not even compile with -Wall compilation flag.
        int result { add(num, ++num) };

        cout << "Result: " << result << endl;

        return EXIT_SUCCESS;
    }
}
