objects = main.o
CXXFLAGS += -g -pedantic-errors -Wall -Weffc++ -Wextra -Wsign-conversion -Werror -std=c++17 -std=gnu++17

all : $(objects)
	g++ -o main $(objects)

main.o : 

.PHONY : clean
clean:
	rm main $(objects)
