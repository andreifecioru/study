#include "quiz_03.h"

#include <iostream>

namespace ch_10_lecture_xx_quiz_03 {
    namespace {
        int binarySearchRec(const int* array, int target, int min, int max) {
            if (min == max)
                return (array[min] == target) ? min : -1;

            if (array[min] == target)
                return min;

            if (array[max] == target)
                return max;

            if (max - min == 1)
                return -1;

            int mid { min + (max - min) / 2 };
            if (target < array[mid])
                return binarySearchRec(array, target, min, mid);
            else
                return binarySearchRec(array, target, mid, max);
        }

    }

    int run() {
        constexpr int array[]{ 3, 6, 8, 12, 14, 17, 20, 21, 26, 32, 36, 37, 42, 44, 48 };

        constexpr int numTestValues{9};
        constexpr int testValues[numTestValues]{ 0, 3, 12, 13, 22, 26, 43, 44, 49 };
        int expectedValues[numTestValues]{ -1, 0, 3, -1, -1, 8, -1, 13, -1 };

        for (int count{0}; count < numTestValues; ++count) {
            int index{ binarySearchRec(array, testValues[count], 0, static_cast<int>(std::size(array)) - 1) };
            if (index == expectedValues[count])
                std::cout << "test value " << testValues[count] << " passed!" << std::endl;
            else
                std::cout << "test value " << testValues[count] << " failed. There's something wrong with your code!" << std::endl;
        }

        return EXIT_SUCCESS;
    }
}
