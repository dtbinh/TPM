#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>



int main(int argc, char const *argv[])
{
	key_t cle = ftok("file", 10);
	int idSem = semget(cle, 1, IPC_CREAT | 0666);
	return 0;
}