#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    pid_t pid;
    int status;

    pid = fork();

    if (pid < 0) {
        perror("Erro no fork");
        exit(1);
    }

    if (pid == 0) {
        // Bloco do FILHO
        printf("[FILHO]: Iniciando tarefa pesada (5 segundos)...\n");
        sleep(5);
        printf("[FILHO]: Tarefa concluída. Vou encerrar agora com status 42.\n");
        exit(42);
    } else {
        // Bloco do PAI
        printf("[PAI]: Meu filho (PID %d) está trabalhando. Vou aguardar...\n", pid);

        // O pai para aqui até o filho dar exit()
        wait(&status);

        if (WIFEXITED(status)) {
            printf("[PAI]: Meu filho terminou normalmente com status: %d\n", WEXITSTATUS(status));
        }

        printf("[PAI]: Agora que meu filho encerrou, eu também vou. Tchau!\n");
    }

    return 0;
}