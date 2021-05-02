#include "Printable.h"

#include <iostream>

namespace ch_16_lecture_04 {
    std::ostream& operator<<(std::ostream& os, const Printable& obj) {
        obj.print(os);
        return os;
    }
}
