#pragma once

#include <iostream>

namespace ch_13_lecture_xx_quiz_02 {
    class Printable {

        friend std::ostream& operator<<(std::ostream& os, const Printable& obj);

    protected:
        virtual void print(std::ostream& os) const = 0;
    };
}



