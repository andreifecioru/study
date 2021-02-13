#include "quiz_02.h"

#include <iostream>


namespace ch_08_lecture_02_quiz_02 {

    namespace {
        std::string getInputText(const std::string& prompt) {
            std::cout << prompt;

            std::string inText {};
            std::getline(std::cin, inText);

            return inText;
        }
    }

    int run() {
        std::string inStr { getInputText("Enter your text here: ") };
        std::string findStr { getInputText("Find: ") };
        std::string replaceStr { getInputText("Replace: ") };

        if ( auto pos = inStr.find(findStr); pos != std::string::npos) {
            inStr.replace(pos, findStr.length(), replaceStr);

            std::cout << "New string is: " << inStr << std::endl;
        } else {
            std::cout << "Could not find token: <" << findStr << "> in input string: \n" << inStr << std::endl;
        }

        return EXIT_SUCCESS;
    }
}
