#include <cstdint>
#include <iostream>

using namespace std;

void not_used(int32_t &x) { x = x; }

int main() {
    cout << "Int: " << sizeof(int) << endl;
    cout << "Short: " << sizeof(short) << endl;
    cout << "Long: " << sizeof(long) << endl;
    cout << "Long long: " << sizeof(long long) << endl;
    cout << "Float: " << sizeof(float) << endl;
    cout << "Double: " << sizeof(double) << endl;
    cout << "Long double: " << sizeof(long double) << endl;
    cout << "SizeT: " << sizeof(size_t) << endl;

    int8_t v1 = 65;
    cout << "V1: " << v1 << endl; // printed out as a char ('A')

    int32_t v2;  // uninitialized var
    not_used(v2);
    cout << "V2: " << v2 << endl;

    double zero{0.0};
    double inf{1.0 / zero};
    double nan{zero / zero};
    cout << "Inf: " << inf << endl;
    cout << "Nan: " << nan << endl;

    if (zero) {
        cout << "CHECK 1" << endl;
    } else {
        cout << "CHECK 2" << endl;
    }

    cout << endl
         << endl;

    return 0;
}
