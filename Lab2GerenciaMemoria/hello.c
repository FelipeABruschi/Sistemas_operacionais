#include <stdio.h>
#include <unistd.h>
int global_init = 42;
// DATA
int global_uninit;
// BSS
int main() {
    printf("Hello World!\n");
    sleep(600);
}