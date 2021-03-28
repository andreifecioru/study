#include "StaticConstrInnerClass.h"

namespace ch_11_lecture_14 {
    // must define the static member
    StaticConstrInnerClass::values_t StaticConstrInnerClass::s_values {};

    // implementation of the custom initialization logic
    StaticConstrInnerClass::static_init::static_init() {
        for (auto i {0}; i < StaticConstrInnerClass::s_capacity; ++i) {
            StaticConstrInnerClass::s_values[i] = i;
        }
    }

    // trigger the custom initialization logic
    StaticConstrInnerClass::static_init StaticConstrInnerClass::static_initializer { };
}
