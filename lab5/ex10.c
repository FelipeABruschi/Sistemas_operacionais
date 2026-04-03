#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void* worker(void* id) {
    int num = *(int*)id;
    while(1) {
        printf("[Worker %d] Aguardando requisicao...\n", num);
        sleep(rand() % 5 + 1);
        printf("[Worker %d] Processando...\n", num);
    }
}

int main() {
    pthread_t threads[5];
    int ids[5];
    for(int i=0; i<5; i++) {
        ids[i] = i;
        pthread_create(&threads[i], NULL, worker, &ids[i]);
    }
    printf("Servidor rodando com 5 Workers. PID: %d\n", getpid());
    while(1)
        sleep(10);
    // Mantem o main vivo
    
    return 0;
}