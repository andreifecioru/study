#include "Printable.h"

#include <iostream>

namespace ch_13_lecture_02 {
    std::ostream& operator<<(std::ostream& os, const Printable& obj) {
        obj.print(os);
        return os;
    }
}
