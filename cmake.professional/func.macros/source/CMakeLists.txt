cmake_minimum_required(VERSION 3.19)

project(FuncAndMacros)

include(func.cmake)


message("")
greet("Andrei")
message("")
greet("Andrei" 10 100 "Mihai")
message("")
