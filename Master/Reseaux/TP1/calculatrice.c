#include <stdio.h>
#include <stdlib.h>
#include <sys/msg.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include "structure.h"

//fonction qui prend une structure req et rend un 
int calcule(struct req requette)
{
	int res = 0;

	//affichage de la requette pour le test
	printf("%d %d %d\n", requette.op1, requette.type, requette.op2);

	switch(requette.type)
	{
		case '+':
			res = requette.op1 + requette.op2;
			break;

		case '-':
			res = requette.op1 - requette.op2;
			break;
		case '*':
			res = requette.op1 * requette.op2;
			break;
		case '/':
			if(requette.op2 != 0)
				res = requette.op1 / requette.op2;
			break;
		default:
			printf("switch default\n");
			break;
	}
	return res;
}


int main(int argc, char const *argv[])
{
	key_t cle = ftok("fichier", 10);
	int id = msgget(cle, IPC_CREAT | IPC_EXCL | 0666);
	if (id == -1)
	{
		printf("Erreur !!!!!!!\n");
		exit(0);
	}
	else
	{
		while(1)
		{
			struct req requette;
			struct rep reponse;
			msgrcv(id, &requette, sizeof(struct req) - sizeof(long), 1, 0);

			reponse.res = calcule(requette);
			reponse.etiq = 2;
			msgsnd(id, &reponse, sizeof(struct rep) - sizeof(long), 0);
		}
	}

	return 0;
}