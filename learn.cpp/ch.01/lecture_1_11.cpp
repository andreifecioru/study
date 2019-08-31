#include <iostream>

using namespace std;


int main() {

    cout << "Enter an integer: ";
    int32_t num{};
    cin >> num;

    int32_t result_double{num * 2};
    cout << "Double " << num << " is: " << result_double << endl;

    int32_t result_triple{num * 3};
    cout << "Triple " << num << " is: " << result_triple << endl;

    return 0;
}