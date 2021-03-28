#pragma once
#include "Printable.h"

#include <string>
#include <iostream>
#include <array>

namespace ch_11_lecture_xx_quiz_03 {
    class Monster
        : public Printable {

    public:
        enum Type {
            dragon,
            goblin,
            ogre,
            orc,
            skeleton,
            troll,
            vampire,
            zombie,

            max_monster_types,
        };

        Monster(Type type, std::string name, std::string roar, int hp)
            : m_type { type }
            , m_name { std::move(name) }
            , m_roar { std::move(roar) }
            , m_hp { hp } {}

    protected:
        void print(std::ostream& os) const override;

    private:
        Type m_type;
        std::string m_name;
        std::string m_roar;
        int m_hp;

        static constexpr std::array<std::string_view, max_monster_types> s_monsterTypeStrings {
            "dragon",
            "goblin",
            "ogre",
            "orc",
            "skeleton",
            "troll",
            "vampire",
            "zombie",
        };

        static std::string_view monsterTypeToString(Type type);
    };
}





