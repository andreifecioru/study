#include "Printable.h"

#include <iostream>

namespace ch_11_lecture_14_quiz_01 {
    std::ostream& operator<<(std::ostream& os, const Printable& obj) {
        obj.print(os);
        return os;
    }
}
