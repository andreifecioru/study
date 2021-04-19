#include "quiz_01.h"
#include "MyString.h"

#include <iostream>


namespace ch_13_lecture_10_quiz_01 {
    int run() {
        MyString s1 { "The quick fox jumps over the lazy dog." };
        std::cout << s1 << std::endl;

        std::cout << s1(4, 5);
        
        return EXIT_SUCCESS;
    }
}
