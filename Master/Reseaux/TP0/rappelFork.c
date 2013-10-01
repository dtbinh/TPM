#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


void forkEnLargeur(int nbEnfant)
{
    pid_t pid;
    while(pid != 0 && nbEnfant >= 0)
    {
        pid = fork();
        if(pid == 0)
        {
            printf("enfant %d \n", nbEnfant);
            sleep(10);
        }
        else
        {
            if(nbEnfant == 0)
                while(wait(0) != -1);
        }
        nbEnfant--;
    }
}

void forkEnHauteur(int nbEnfant)
{
    pid_t pid = 0;
    while(pid == 0 && nbEnfant >= 0)
    {
        pid = fork();
        if(pid == 0)
        {
            printf("enfant %d \n", nbEnfant);
            if(nbEnfant == 0)
                sleep(5);
        }
        else
        {
            //permet d'attendre que tous les fils soit mort avant de finir le processus pere
            //sinon on aura des processus fils sans pere (zombie)
            while(wait(0) != -1);
        }
        nbEnfant--;
    }
}

void forkBinaire(int hauteur)
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
                //printf("enfant %d \n", nbEnfant);
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
    //forkEnLargeur(3);
    //forkEnHauteur(3);
    forkBinaire(3); 
}
