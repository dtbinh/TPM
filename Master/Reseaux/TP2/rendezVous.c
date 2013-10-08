#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>

union semun {
	int val;
	struct semid_ds *buf;
	unsigned short *array;
	struct seminfo *__buf;
}


int main(int argc, char const *argv[])
{

	key_t cle = ftok("file", 10);
	int idSem = semget(cle, 1, IPC_CREAT | 0666);


	struct sembuf op[] = {
		{(u_short)0, (short)-1, SEM_UNDO}, //p
		{(u_short)0, (short)+1, SEM_UNDO}, //v
		{(u_short)0, (short)0, SEM_UNDO} //z
	};

	/

	if (idSem > 0)
	{
		semun sAmoi;
		sAmoi.val = 2;
		if(semctl(idSem, 0, SETVAL, sAmoi) == -1)
		{
		 	perror("probleme d'init !!!!!!");
		 	//suite
		}
	}
	else
	{
		idSem = semget(cle, 1, 0666);
		//semop(idSem, op[0], 1);
		//semop(idSem, op[1], 1);
	}





	return 0;
}