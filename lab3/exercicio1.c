#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(){
    int pid, status;

    pid = fork();

    if(pid == -1){
        perror("fork falhou!\n");
        exit(-1);
    } else if(pid == 0){
        
        pid = fork();
        if(pid == -1){
            printf("fork falhou!\n");
            exit(0);
        }
        else{
            printf("processo neto\t pid neto: %d\t pid filho: %d\n", getpid(), getppid());
            exit(0);
        }

    } else{ // pai
        wait(&status);
        printf("processo pai\t pid: %d\n", getpid());
    }

    exit(0);
}