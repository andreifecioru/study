#include "quiz_02.h"

#include <iostream>


namespace ch_09_lecture_11_quiz_02 {
    namespace {

        const int* find(const int* begin, const int* end, const int target) {
            for (const int* cursor{begin}; cursor != end; ++cursor) {
                if (*cursor == target)
                    return cursor;
            }

            return end;
        }
    }

    int run() {
        constexpr int array[] { 2, 5, 4, 10, 8, 20, 16, 40 };
        constexpr int target {20};

        const int* found { find(std::begin(array), std::end(array), target) };

        if (found != std::end(array)) {
            std::cout << "Element " << *found << " was found!" << std::endl;
        } else {
            std::cout << "Element " << target << " was NOT found!" << std::endl;
        }

        return EXIT_SUCCESS;
    }
}
