include_guard()

function(greet name)
    message("${name} says hi!")

    message("Number of args: ${ARGC}")
    message("List of args: ${ARGV}")
    message("List of unnamed args: ${ARGN}")
endfunction()
