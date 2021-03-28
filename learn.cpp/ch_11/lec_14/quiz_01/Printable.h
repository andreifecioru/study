#pragma once

#include <iostream>

namespace ch_11_lecture_14_quiz_01 {
    class Printable {

        friend std::ostream& operator<<(std::ostream& os, const Printable& obj);

    protected:
        virtual void print(std::ostream& os) const = 0;
    };
}




