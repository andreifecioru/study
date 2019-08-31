#include <iostream>
#include <cstdlib>

using namespace std;

int echo(int x) {
    cout << "echo: " << x << endl;
    return x;
}

void doSomething(int x, int y) {
    cout << "x: " << x << endl;
    cout << "y: " << y << endl;
}

int main() {

    // NOTE: there is no guarantee on the order of function calling
    // when evaluating the params.
    doSomething(echo(1), echo(2));

    return EXIT_SUCCESS;
}