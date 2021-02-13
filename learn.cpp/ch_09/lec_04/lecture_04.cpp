#include "lecture_04.h"

#include <iostream>
#include <cassert>

namespace ch_09_lecture_04 {
    namespace {
        enum class SortType {
            ascending,
            descending
        };

        void printArray(const int array[], size_t size) {
            std::cout << "[ ";
            for (auto i {0}; i < size; ++i) {
                std::cout << array[i] << ' ';
            }
            std::cout << "]\n";
        }

        size_t indexOfMin(const int array[], size_t size) {
            assert(size > 0);
            auto minValue { array[0] };
            auto minIdx {0};

            for (auto i {1}; i < size; ++i) {
                if (array[i] < minValue) {
                    minValue = array[i];
                    minIdx = i;
                }
            }

            return minIdx;
        }

        size_t indexOfMax(const int array[], size_t size) {
            assert(size > 0);
            auto maxValue { array[0] };
            auto maxIdx {0};

            for (auto i {1}; i < size; ++i) {
                if (array[i] > maxValue) {
                    maxValue = array[i];
                    maxIdx = i;
                }
            }

            return maxIdx;
        }

        void selectionSort(int array[], const size_t size, const SortType sortType) {
            for (auto i {0}; i < size - 1; ++i) {
                if (sortType == SortType::ascending)
                    std::swap(array[i], array[i + indexOfMin(array + i, size - i)]);
                else if (sortType == SortType::descending)
                    std::swap(array[i], array[i + indexOfMax(array + i, size - i)]);
                else
                    std::cerr << "Invalid sort type.";

                // std::cout << '\t'; printArray(array, size);
            }
        }

    }
    int run() {
        int values[] { 30, 50, 20, 10,40 };
        size_t numValues { std::size(values) };

        printArray(values, numValues);

        selectionSort(values, numValues, SortType::ascending);
        printArray(values, numValues);

        selectionSort(values, numValues, SortType::descending);
        printArray(values, numValues);

        return EXIT_SUCCESS;
    }
}
