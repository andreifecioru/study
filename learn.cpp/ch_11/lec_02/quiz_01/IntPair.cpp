#include "IntPair.h"

#include <iostream>

void IntPair::set(const int first, const int second) {
    m_first = first;
    m_second = second;
}

void IntPair::print() const {
    std::cout << "Pair(" << m_first << ", " << m_second << ")" << std::endl;
}
