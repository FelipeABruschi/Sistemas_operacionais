#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(){
    int pid, status;
    int resp = 1;
    int i = 5;

    while(i > 0){
        pid = fork();
        if(pid == -1){
            perror("fork falhou!\n");
        }else if(pid == 0){
            resp *= i;
            return resp;
        }else{
            wait(&status);
            if(WIFEXITED(status)){
                resp = WEXITSTATUS(status);
                i--;
            }
        }
    }

    printf("%d! = %d\n", 5, resp);
    exit(0);
}