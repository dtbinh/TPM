#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <unistd.h>

union semun {
	int val;
	struct semid_ds *buf;
	unsigned short *array;
	struct seminfo *__buf;
};


int main(int argc, char const *argv[])
{

	key_t cle = ftok("troll", 42);
	int idSem = semget(cle, 1, IPC_CREAT | IPC_EXCL | 0666);


	struct sembuf semP;
	semP.sem_num = 0;
	semP.sem_op = -1;
	semP.sem_flg = 0;

	struct sembuf semZ;
	semZ.sem_num = 0;
	semZ.sem_op = 0;
	semZ.sem_flg = 0;



	if (idSem < 0)
	{
		printf("j'ai l'adresse de rdv...\n");
		idSem = semget(cle, 1, 0666);		
	}
	else
	{
		union semun sAmoi;
		sAmoi.val = 3;
		if(semctl(idSem, 0, SETVAL, sAmoi) == -1)
		{
		 	perror("probleme d'init !!!!!!");
		 	//suite
		}
	}

	//printf("Valeur du semaphore = %d\n", semctl(idSem, 0, GETVAL));
	printf("je suis arrivé sur le lieu de rdv \n");
	if(semop(idSem, &semP, 1) == -1) //P décrementation
		perror("operation fail");
	printf("Valeur du semaphore = %d\n", semctl(idSem, 0, GETVAL));
	//zone critique
	printf("zone critique !!!!! \n");
	//sleep (2);

	//voir comment on sort de la zone critique quand tous le monde est arrivé dans au lieux de rdv
	semop(idSem, &semZ, 1); //Z
	printf("fin du proc\n");

	return 0;
}