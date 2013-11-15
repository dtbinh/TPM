//serveur
#include <iostream> 
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include "sock.h"
#include "sockdist.h"
#include <arpa/inet.h>
#include <string.h>
#include <pwd.h>
#include <unistd.h>


using namespace std;

void * serveur(void *arg)
{
	while(true)
	{
		int * descs[2] = (int *) arg;
		int s = read(*descs[0], buffer, sizeof(buffer));

		if(s != -1)
		{
			cout << "message reçu: " << buffer << endl;
		}
		int s2 = write(*descs[1], buffer, sizeof(buffer));
	}

	pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
	//création de la boite reseau locale
	Sock brServeur(SOCK_STREAM, 31031, 0);
	int desc;
	if(brServeur.good())
		desc = brServeur.getsDesc();
	else
	{
		cout << "Pb BR" << endl;
		exit(1);
	}

	int res = listen(desc, 2); //liste d'attente de taille 2, si plus de client, rejeté

	struct sockaddr_in brCv1;
	socklen_t lg1 = sizeof(sockaddr_in);
	int descBrCv1 = accept(desc, (struct sockaddr*)&brCv1, &lg1);

	struct sockaddr_in brCv2;
	socklen_t lg2 = sizeof(sockaddr_in);
	int descBrCv2 = accept(desc, (struct sockaddr*)&brCv2, &lg2);

	char buffer[256];
	while(true)
	{
		//creation des threads
		pthread_t idTh[2];
		int descs[2] = {descBrCv1, descBrCv2};
		for (int i = 0; i < 2; i++)
		{
			if(pthread_create(&idTh[i], NULL, serveur, (void *) & descs) != 0)
				printf("erreur creation thread %d \n", i);
		}

		//attendre la fin des threads
		for (int i = 0; i < 2; i++)
		{
			pthread_join(idTh[i], NULL);
		}
	}
	close(desc);
	return 0;
}

