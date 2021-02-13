#include "lecture_06.h"

#include <iostream>


namespace ch_05_lecture_06 {
    using namespace std;

    namespace {
        bool almostEqual(double v1, double v2, double abs_eps, double rel_eps) {
            double dist { abs(v1 - v2) };

            // if input values are really close we need to compare
            // against an absolute epsilon value
            if (dist <= abs_eps) return true;

            // otherwise we compare the distance to a value relative
            // to the magnitude of the inputs
            return (dist <= max(abs(v1), abs(v2)) * rel_eps);
        }
    }

    int run() {
        // this is really close to 1.0 (but has some rounding error)
        double num { 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1};

        // abs_eps value should be smaller than rel_eps
        cout << boolalpha;
        cout << almostEqual(1.0 - num, 0.0, 1e-12, 1e-8) << endl;

        return EXIT_SUCCESS;
    }
}
