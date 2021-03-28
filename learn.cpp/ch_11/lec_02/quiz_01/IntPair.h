#pragma once


class IntPair {
    int m_first { 0 };
    int m_second { 0 };

public:
    IntPair() = default;
    IntPair(int first, int second)
        : m_first { first}
        , m_second { second } {}

    void set(int first, int second);
    void print() const;
};



