#include "lecture_12.h"

#include <iostream>
#include <cstdlib>
#include <string_view>
#include <tuple>
#include <type_traits>

namespace ch_00_lecture_12 {
    namespace a::b::c
    {
        inline constexpr std::string_view str{ "hello" };
    }

    template <class... T>
    std::tuple<std::size_t, std::common_type_t<T...>> sum(T... args)
    {
        return { sizeof...(T), (args + ...) };
    }


    int run() {
        auto [iNumbers, iSum]{ sum(1, 2, 3) };
        std::cout << a::b::c::str << ' ' << iNumbers << ' ' << iSum << '\n';

        std::array arr{ 1, 2, 3 };

        std::cout << std::size(arr) << '\n';

        return EXIT_SUCCESS;
    }
}
