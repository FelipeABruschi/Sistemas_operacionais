#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

void* tarefa_thread(void* arg) {
    int id = *((int*)arg);
    int tempo = id * 30; // 30s, 60s, 90s, 120s
    printf("Thread %d iniciada. Vou rodar por %d segundos.\n", id, tempo);
    sleep(tempo);
    printf("Thread %d finalizada ap´os %d segundos.\n", id, tempo);
    pthread_exit(NULL);
}

int main() {
    pthread_t threads[4];
    int ids[4];
    printf("Processo Principal (PID: %d) iniciando...\n", getpid());
    for (int i = 0; i < 4; i++) {
        ids[i] = i + 1;
        pthread_create(&threads[i], NULL, tarefa_thread, &ids[i]);
    }
    for (int i = 0; i < 4; i++) 
        pthread_join(threads[i], NULL);

    printf("Todas as threads terminaram. Encerrando processo principal.\n");
    return 0;
}