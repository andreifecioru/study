#pragma once

#include <iostream>
#include <array>

namespace ch_09_lecture_xx_quiz_01 {
    enum class ItemType {
        potion = 0,
        torch,
        arrow,

        max_item_id
    };

    using inventory_t = std::array<int, static_cast<size_t>(ItemType::max_item_id)>;

    int run();
}