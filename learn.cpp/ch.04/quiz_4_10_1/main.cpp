#include <cstdlib>
#include <iostream>

using namespace std;

bool isPrime(int num);


int main() {
    cout << "Enter an integer: ";

    int num{};
    cin >> num;

    cout << std::boolalpha; // print booleans as "true" and "false"
    cout << "Is " << num << " prime? " << isPrime(num) << endl;

    return EXIT_SUCCESS;
}

bool isPrime(int num) {
    if (num <= 3) return true;

    for (int i = 2; i < num / 2; i++) {
        if (num % i == 0) return false;
    }

    return true;
}
