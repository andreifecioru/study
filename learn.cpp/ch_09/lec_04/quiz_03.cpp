#include "quiz_03.h"

#include <iostream>


namespace ch_09_lecture_04_quiz_03 {
    namespace {
        void printArray(const int array[], const size_t size) {
            std::cout << "[ ";
            for (auto i {0}; i < size; ++i) {
                std::cout << array[i] << ' ';
            }
            std::cout << "]\n";
        }

        void bubbleSort(int array[], const size_t size) {
            bool sorted {false};
            size_t passCount {0};

            do {
                sorted = true;
                for(auto i{0}; i < size - passCount - 1; ++i) {
                    if (array[i] > array[i + 1]) {
                        std::swap(array[i], array[i + 1]);
                        sorted = false;
                    }
                }
                ++passCount;
            } while (!sorted);
        }
    }

    int run() {
        int array[] {6, 3, 2, 9, 7, 1, 5, 4, 8};
        int arraySize { std::size(array) };

        printArray(array, arraySize);
        bubbleSort(array, arraySize);
        printArray(array, arraySize);

        return EXIT_SUCCESS;
    }
}
