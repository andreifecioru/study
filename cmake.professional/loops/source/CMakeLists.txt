cmake_minimum_required(VERSION 3.19)

project(loops)

message("\n--------------------\n")

# iterating over a list
set(list1 one two three four)
set(list2 five six seven eight)
foreach(aValue IN LISTS list1 list2)
    message("Value: ${aValue}")
endforeach()

message("\n--------------------\n")

# iterate over a list of items provided inline
foreach(name IN ITEMS andrei mihai radu)
    message("Name: ${name}")
endforeach()

message("\n--------------------\n")

# iterating over a range of values
foreach(aValue RANGE 10 20 2)
    message("Value in range: ${aValue}")
endforeach()

