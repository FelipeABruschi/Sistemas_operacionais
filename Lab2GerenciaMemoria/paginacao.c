#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define DEZESSEIS_MEGA (16 * 1024 * 1024) // 16 MB

int main() {
    int pid = getpid();
    printf("=== PROCESSO INICIADO (PID: %d) ===\n", pid);
    printf("Passo A: Antes de alocar memoria. Verifique o consumo.\n");
    printf("Pressione ENTER para alocar 16 MB (mas sem usar)...");
    getchar();
    // Alocando 16 Megabytes de memoria virtual
    char *memoria = (char *)malloc(DEZESSEIS_MEGA);
    if (memoria == NULL) {
        perror("Erro ao alocar");
        return 1;
    }
    printf("\nPasso B: 16 MB alocados virtualmente.\n");
    printf("Pressione ENTER para ESCREVER nas paginas de memoria...");
    getchar();
    // Escrevendo em cada pagina (pulando de 4096 em 4096 bytes)
    // Isso forca o Kernel a alocar os quadros fisicos na RAM
    for (int i = 0; i < DEZESSEIS_MEGA; i += 4096) {
        memoria[i] = 'X';
    }
    printf("\nPasso C: Dados escritos na memoria (Paginas fisicas associadas).\n");
    printf("Pressione ENTER para encerrar o programa...");
    getchar();
    free(memoria);
    return 0;
}