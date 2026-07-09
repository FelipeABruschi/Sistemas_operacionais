#include <stdio.h>
int global = 10;

int main() {
    int local = 20;
    printf("Endereco da variavel global: %p\n", &global);
    printf("Endereco da variavel local : %p\n", &local);
    getchar();
    return 0;
}