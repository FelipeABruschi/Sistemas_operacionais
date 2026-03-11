#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <linux/limits.h>
#include <unistd.h>
#include <sys/stat.h>
#include <dirent.h>
#include <time.h>

// man 3 cwd
int mycwd(){
   char cwd[PATH_MAX];

   if (getcwd(cwd, sizeof(cwd)) != 0)
       printf("%s\n", cwd);
   else{
       perror("getcwd() error\n");
       return 1;
   }
   return 0;
}

int mymkdir(){
   char in[256];
   scanf("%s", in);

   if(mkdir(in, 0700) != 0){
       perror("mkdir() error\n");
       return 1;
   }
   return 0;
}

int myrmdir(){
   char in[256];
   scanf("%s", in);

   if(rmdir(in) != 0){
       perror("rmdir() error\n");
       return 1;
   }
   return 0;
}

int mycd(){
    char in[256];
    scanf("%s", in);

    if(chdir(in) != 0){
        perror("cd() error\n");
        return 1;
    }

   return 0;
}


int mystat(){
    struct stat buf;

    char in[256];
    scanf("%s", in);

    if(stat(in, &buf) != 0){
        perror("stat() error\n");
        return 1;
    }
    printf("tamanho: %ld\n", buf.st_size);
    printf("modo: %o\n", buf.st_mode);
    printf("ultima modificacao: %s", ctime(&buf.st_atime));
    printf("device: %ld\n", buf.st_dev);
    printf("dono: %d\n", buf.st_uid);

   return 0;
}

int myls(){
   struct dirent *dir;
   DIR *d;

   d = opendir(".");
   if(d != NULL){
       while((dir = readdir(d)) != NULL)
           printf("%s\n", dir->d_name);
    closedir(d);
   }

   return 0;
}

int main(int argc, char** argv){
   bool teste = true;

   while(teste){
       char in[256];
       printf("<myshel> ");
       scanf("%s", in);

       if(strcmp(in, "exit") == 0)
           teste = false;
       else if(strcmp(in, "cwd") == 0){
           mycwd();
       }
       else if(strcmp(in, "mkdir") == 0){
           mymkdir();
       }
       else if(strcmp(in, "rmdir") == 0){
           myrmdir();           
       }
       else if(strcmp(in, "cd") == 0){
           mycd();           
       }
       else if(strcmp(in, "stat") == 0){
           mystat();           
       }
       else if(strcmp(in, "ls") == 0){
           myls();
       }
       else
           printf("comando inválido.\n");
   }
   return 0;
}