add_library(maths
    src/ops.cpp include/maths/ops.hpp
)

target_include_directories(maths
    PUBLIC include
    PRIVATE src
)

target_compile_options(maths
    PUBLIC -std=c++17
    PRIVATE -Wall -Werror -pedantic-errors
)

