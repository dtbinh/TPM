#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>

//donn´ee statique
pthread_mutex_t verrou = PTHREAD_MUTEX_INITIALIZER;

struct matricule
{
	int chiffre[3];
	char lettre[3];
	int chiffreFix[2];
};

void * monMatricule(void *par)
{
	pthread_t moi = pthread_self();
	printf("thread : %u, proc : %d \n", moi, getpid());
	//demande du verrou
	pthread_mutex_lock(&verrou);

	//traitement
	struct matricule * tmpMat = (struct matricule *) par;

	printf("nouveau matricule: %d%d%d-%c%c%c-%d%d\n",
		tmpMat->chiffre[0], tmpMat->chiffre[1], tmpMat->chiffre[2],
		tmpMat->lettre[0], tmpMat->lettre[1], tmpMat->lettre[2],
		tmpMat->chiffreFix[0], tmpMat->chiffreFix[1]);
	tmpMat->chiffre[2] += 1;

	sleep(1);

	//liberation du verrou
	pthread_mutex_unlock(&verrou);
	pthread_exit(NULL);
}


int main(int argc, char const *argv[])
{
	//declaration et init de la struct matricule
	struct matricule mat;
	for (int i = 0; i < 3; i++)
	{
		mat.chiffre[i] = 0;
		mat.lettre[i] = 'a';
	}
	mat.chiffreFix[0] = 3;
	mat.chiffreFix[1] = 4;

	//creation des threads
	pthread_t idTh[3];
	for (int i = 0; i < 3; i++)
	{
		if(pthread_create(&idTh[i], NULL, monMatricule, (void *) & mat) != 0)
			printf("erreur creation thread %d \n", i);
	}

	//attendre la fin des threads
	for (int i = 0; i < 3; i++)
	{
		pthread_join(idTh[i], NULL);
	}

	printf("fin des demandes !!!\n");
	return 0;
}