#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>

void *monThread (void * par){
	int *p = (int *) par;
	pthread_t moi = pthread_self();
	int i = 3;
	while(i > 0)
	{
		(*p)++; 
		printf("thread : %u , proc : %d, p = %d \n", moi, getpid(), *p);
		i--;
		sleep(1);
	}
	pthread_exit(NULL);
}

int main(int argc, char const *argv[])
{
	int p = 0;

	pthread_t idTh[4];
	for (int i = 0; i < 4; i++)
	{
		if (pthread_create(&idTh[i], NULL, monThread, (void *) &p) != 0)
			printf("erreur creation thread %d \n", i);
	}
	//attendre la fin des threads
	for (int i = 0; i < 4; i++)
	{
		pthread_join(idTh[i], NULL);
	}

	printf("a la fin p vaut %d \n", p);

	return 0;
}