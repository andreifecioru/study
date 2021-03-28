#include "quiz_03.h"
#include "Stack.h"

#include <iostream>


namespace ch_11_lecture_11_quiz_03 {
    namespace {}

    int run() {

        Stack stack;

        stack.reset();
        std::cout << stack << std::endl;

        stack.push(5);
        stack.push(3);
        stack.push(8);
        std::cout << stack << '\n' << std::endl;

        for (auto i { 0 }; i < 3; ++i) {
            int value { stack.pop() };
            std::cout << "Popped value: " << value << '\n';
            std::cout << stack << '\n' << std::endl;
        }

        try {
            int value { stack.pop() };
            std::cout << "Popped value: " << value << '\n';
        } catch (const Stack::OpFailedException &ex) {
            std::cerr << ex.what() << std::endl;
        }

        try {
            for (auto i { 0 }; i < 11; ++i) {
                stack.push(i);
            }
        } catch (const Stack::OpFailedException &ex) {
            std::cerr << ex.what() << std::endl;
            std::cout << stack << '\n' << std::endl;
        }

        return EXIT_SUCCESS;
    }
}
