#include "lecture_03.h"

#include <iostream>


namespace ch_02_lecture_03 {
    using namespace std;

    namespace {
        int echo(int x) {
            cout << "echo: " << x << endl;
            return x;
        }

        void doSomething(int x, int y) {
            cout << "x: " << x << endl;
            cout << "y: " << y << endl;
        }
    }

    int run() {
        // NOTE: there is no guarantee on the order of function calling
        // when evaluating the params.
        doSomething(echo(1), echo(2));

        return EXIT_SUCCESS;
    }
}
