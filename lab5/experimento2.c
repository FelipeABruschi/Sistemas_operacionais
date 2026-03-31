#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

int main() {
    pid_t pid = fork();
    if (pid == 0) {
    // Filho executa outro programa
        char *args[] = {"/bin/ls", "-l", NULL};
        execve("/bin/ls", args, NULL);
    } else {
    // Pai permanece ativo
        printf("Processo pai PID = %d\n", getpid());
        sleep(30);
    }
    return 0;
}