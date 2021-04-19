#include "Printable.h"

#include <iostream>

namespace ch_13_lecture_09_quiz_01 {
    std::ostream& operator<<(std::ostream& os, const Printable& obj) {
        obj.print(os);
        return os;
    }
}
