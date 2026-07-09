#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>

int main() {
    int fd = open("texto.txt", O_RDWR);
    if (fd < 0) {
        perror("Erro ao abrir arquivo");
        return 1;
    }
    struct stat sb;
    fstat(fd, &sb);
    // Mapeando o arquivo diretamente em uma pagina de memoria virtual
    char *arquivo_em_memoria = mmap(NULL, sb.st_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (arquivo_em_memoria == MAP_FAILED) {
        perror("Erro no mmap");
        return 1;
    }
    // O arquivo agora pode ser lido como se fosse um vetor na memoria
    printf("Conteudo lido da pagina de memoria: %s", arquivo_em_memoria);
    // Modificando a memoria diretamente. A MMU e o Kernel sincronizarao com o disco.
    arquivo_em_memoria[0] = 'O';
    printf("Modificacao feita na memoria. Pressione ENTER para finalizar...");
    getchar();
    munmap(arquivo_em_memoria, sb.st_size);
    close(fd);
    return 0;
}