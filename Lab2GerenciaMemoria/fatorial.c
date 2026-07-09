#include <stdio.h>
long fatorial(int n) {
    long parcial;
    printf("inicio: n = %d\n", n);
    if (n < 2)
        parcial = 1;
    else
        parcial = n * fatorial(n - 1);
        printf("final : n = %d, parcial = %ld\n", n, parcial);
    return parcial;
}
int main() {
    printf("Fatorial(4) = %ld\n", fatorial(4));
    return 0;
}