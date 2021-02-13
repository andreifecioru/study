#include "quiz_01.h"

#include <iostream>
#include <bitset>


namespace opt_01_lecture_03_quiz_01 {
    using namespace std;

    namespace {

    }

    int run() {
        constexpr uint_fast8_t option_viewed { 0x01 };
        // constexpr uint_fast8_t option_edidted { 0x02 };
        constexpr uint_fast8_t option_favorited { 0x04 };
        // constexpr uint_fast8_t option_shared { 0x08 };
        constexpr uint_fast8_t option_deleted { 0x10 };

        uint_fast8_t flags { option_favorited };

        cout << boolalpha;

        flags |= option_viewed;
        cout << "[a] > " << bitset<8> { flags } << endl;

        bool was_deleted = static_cast<bool>(flags & option_deleted);
        cout << "[b] > Was deleted: " << was_deleted << endl;

        flags &= ~option_favorited;
        cout << "[c] > " << bitset<8> { flags } << endl;

        return EXIT_SUCCESS;
    }
}
