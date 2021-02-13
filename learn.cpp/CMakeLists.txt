cmake_minimum_required(VERSION 3.17)

project(learn_cpp C CXX)

set(CMAKE_CXX_STANDARD 17)

add_executable(learn_cpp
        main.cpp lectures.h
        ch_00/lec_12/lecture_00_12.h ch_00/lec_12/lecture_00_12.cpp
        ch_01/lec_11/lecture_11.h ch_01/lec_11/lecture_11.cpp
        ch_01/lec_xx/quiz_01.h ch_01/lec_xx/quiz_01.cpp
        ch_02/lec_03/lecture_03.h ch_02/lec_03/lecture_03.cpp
        ch_02/lec_07/lecture_07.h ch_02/lec_07/lecture_07.cpp
        ch_02/lec_07/add.h ch_02/lec_07/add.cpp ch_02/lec_07/io.h ch_02/lec_07/io.cpp
        ch_02/lec_03/quiz_05.h ch_02/lec_03/quiz_05.cpp
        ch_04/lec_03/lecture_03.h ch_04/lec_03/lecture_03.cpp
        ch_04/lec_10/quiz_01.h ch_04/lec_10/quiz_01.cpp
        ch_04/lec_xx/quiz_03.h ch_04/lec_xx/quiz_03.cpp
        ch_04/lec_xx/quiz_04.h ch_04/lec_xx/quiz_04.cpp
        ch_05/lec_03/quiz_02.h ch_05/lec_03/quiz_02.cpp
        ch_05/lec_04/lecture_04.h ch_05/lec_04/lecture_04.cpp
        ch_05/lec_06/lecture_06.h ch_05/lec_06/lecture_06.cpp
        ch_06/lec_03/quiz_01.h ch_06/lec_03/quiz_01.cpp
        ch_06/lec_xx/quiz_03.h ch_06/lec_xx/quiz_03.cpp
        opt_01/lec_03/quiz_01.h opt_01/lec_03/quiz_01.cpp
        opt_01/lec_04/quiz_06.h opt_01/lec_04/quiz_06.cpp
        ch_08/lec_01/quiz_01.h ch_08/lec_01/quiz_01.cpp
        ch_08/lec_02/quiz_02.h ch_08/lec_02/quiz_02.cpp
        ch_08/lec_05/quiz_01.h ch_08/lec_05/quiz_01.cpp
        ch_08/lec_05/quiz_02.h ch_08/lec_05/quiz_02.cpp ch_08/lec_xx/quiz_01.h ch_08/lec_xx/quiz_01.cpp)

