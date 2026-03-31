#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

int main() {
    pid_t pid;
    pid = fork();

    if (pid == 0) {
// Processo filho
        printf("Sou o processo FILHO. PID = %d\n", getpid());
        sleep(30);
    } else {
// Processo pai
        printf("Sou o processo PAI. PID = %d, filho = %d\n", getpid(), pid);
        sleep(30);
    }

    return 0;
}