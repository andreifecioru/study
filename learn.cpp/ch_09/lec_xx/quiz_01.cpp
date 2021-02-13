#include "quiz_01.h"

#include <iostream>
#include <array>
#include <numeric>


namespace ch_09_lecture_xx_quiz_01 {
    namespace {

        int countTotalItems(const inventory_t& inventory) {
            return std::reduce(inventory.begin(), inventory.end());
        }

        void displayInventory(const inventory_t& inventory) {
            std::cout << "---------[ Inventory ]-----------\n"
                      << "Health potions: " << inventory[static_cast<std::size_t>(ItemType::potion)] << '\n'
                      << "Torches: " << inventory[static_cast<std::size_t>(ItemType::torch)] << '\n'
                      << "Arrows: " << inventory[static_cast<std::size_t>(ItemType::arrow)] << '\n'
                      << "\n------------------------------------\n"
                      << "Item count: " << countTotalItems(inventory)
                      << std::endl;
        }
    }

    int run() {
        inventory_t items { 2, 5, 10 };

        displayInventory(items);

        return EXIT_SUCCESS;
    }
}
