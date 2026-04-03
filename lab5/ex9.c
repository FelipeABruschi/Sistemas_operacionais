#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <math.h>
#include <stdlib.h>

void* cpu_intensa(void* arg) {
    while(1) 
        sqrt(rand()); // Consome 100% de um nucleo
}

void* entrada_saida(void* arg) {
    while(1) { 
        printf("Log de Operacao...\n"); 
        sleep(1);
    }
}

int main() {
    pthread_t t1, t2, t3;
    pthread_create(&t1, NULL, cpu_intensa, NULL);
    pthread_create(&t2, NULL, cpu_intensa, NULL);
    pthread_create(&t3, NULL, entrada_saida, NULL);
    pthread_join(t1, NULL);
    return 0;
}