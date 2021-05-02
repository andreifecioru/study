#include "Printable.h"

#include <iostream>

namespace ch_16_lecture_03_quiz_02 {
    std::ostream& operator<<(std::ostream& os, const Printable& obj) {
        obj.print(os);
        return os;
    }
}