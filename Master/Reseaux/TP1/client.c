#include <stdio.h>
#include <stdlib.h>
#include <sys/msg.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include "structure.h"


int main(int argc, char const *argv[])
{
	key_t cle = ftok("fichier", 10);
	int id = msgget(cle, IPC_CREAT | IPC_EXCL | 0666);
	if (id > 0)
	{
		//detruire la file
	}
	else
		id = msgget(cle, IPC_CREAT | 0666);

	struct req requette;
	requette.etiq = 1;

	printf("type d'oppération\n");
	scanf("%c", &requette.type);

	printf("1er oppérande\n");
	scanf("%d", &requette.op1);

	printf("2eme oppérande\n");
	scanf("%d", &requette.op2);
	
	msgsnd(id, &requette, sizeof(struct req) - sizeof(long), 0);

	struct rep reponse;
	msgrcv(id, &reponse, sizeof(struct rep) - sizeof(long), 2, 0);

	//affichage de la reponse
	printf("%d\n", reponse.res);

	return 0;
}