#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

void* print_hello(void* arg) {
    while(1) {
        printf("Ol´a da Thread! (PID: %d)\n", getpid());
        sleep(5);
    }
    return NULL;
}

int main() {
    pthread_t thread_id;
    printf("Thread Principal iniciada. PID: %d\n", getpid());
    pthread_create(&thread_id, NULL, print_hello, NULL);
    pthread_join(thread_id, NULL);
    return 0;
}