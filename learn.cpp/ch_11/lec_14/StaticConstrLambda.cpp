#include "StaticConstrLambda.h"

namespace ch_11_lecture_14 {
    // initialize the static "s_values" member with a immediately-invoked lambda
    StaticConstrLambda::values_t StaticConstrLambda::s_values {[]() {
        StaticConstrLambda::values_t values{};

        for (auto i { 0 }; i < StaticConstrLambda::s_capacity; ++i) {
            values[i] = i;
        }

        return values;
    }()};
}
