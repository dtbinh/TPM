#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

//prototypes
void forkBinaire(int hauteur, int lecture)
{
    if(hauteur >= 0)
    {
        pid_t pid;
        int nbEnfant = 1;
        while(pid != 0 && nbEnfant >= 0)
        {
            pid = fork();
            if(pid == 0)
            {
                printf("enfant %d \n", nbEnfant);
                if(hauteur == 0)
                    sleep(5);
            }
            else
            {
                if(nbEnfant == 0)
                    while(wait(0) != -1);
            }
            nbEnfant--;
        }
        forkBinaire(hauteur-1);
    }
}


int main()
{
    int desc[2];
    pipe(desc);
    
	return 0;
}