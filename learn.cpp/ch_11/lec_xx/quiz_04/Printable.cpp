#include "Printable.h"

#include <iostream>


namespace ch_11_lecture_xx_quiz_04 {
    std::ostream& operator<<(std::ostream& os, const Printable& obj) {
        obj.print(os);
        return os;
    }
}
