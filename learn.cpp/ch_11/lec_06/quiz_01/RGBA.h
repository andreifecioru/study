#pragma once

#include <cstdint>
#include <iostream>

namespace ch_11_lecture_06_quiz_01 {
    class RGBA {
    public:
        using color_channel_t = std::uint_fast8_t;

        explicit RGBA(color_channel_t red = 0,
             color_channel_t green = 0,
             color_channel_t blue = 0,
             color_channel_t alpha = 255)
            : m_red { red }
            , m_green { green }
            , m_blue { blue }
            , m_alpha { alpha } {}

    private:
        color_channel_t m_red;
        color_channel_t m_green;
        color_channel_t m_blue;
        color_channel_t m_alpha;

        friend std::ostream& operator<<(std::ostream& os, const RGBA& obj);
    };
}



