cmake_minimum_required(VERSION 3.17)

project(learn_cpp C CXX)

set(CMAKE_CXX_STANDARD 17)

file(GLOB_RECURSE CHAPTER_SOURCES
    ch_*/*.h ch_*/*.hpp ch_*/*.cpp
    extra_*/*.h extra_*/*.hpp extra_*/*.cpp
    opt_*/*.h opt_*/*.hpp opt_*/*.cpp
)

add_executable(learn_cpp
        main.cpp lectures.h
        ${CHAPTER_SOURCES})

target_include_directories(learn_cpp PRIVATE
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/external/fmt/include>
)

