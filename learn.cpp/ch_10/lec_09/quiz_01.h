#pragma once

#include <functional>
#include <map>

#define OpType2Int(opType) static_cast<int>(opType)
#define OpType2Char(opType) static_cast<char>(opType)

namespace ch_10_lecture_09_quiz_01 {

    enum class OperationType {
        add = '+',
        subtract = '-',
        multiply = '*',
        divide = '/'
    };

    using Operation = std::function<double(double, double)>;
    using OperationTable = std::map<OperationType, Operation>;


    int run();
}