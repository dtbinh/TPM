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

	int res = listen(desc, 5); //liste d'attente de taille 5, si plus de client, rejeté
	struct sockaddr_in brCv;
	socklen_t lg = sizeof(sockaddr_in);
	int descBrCv = accept(desc, (struct sockaddr*)&brCv, &lg);
	char buffer[256];
	while(true)
	{
		int s = read(descBrCv, buffer, sizeof(buffer));
		if(s != -1)
		{
			cout << "message reçu: " << buffer << endl;
		}
		
		char msg[] = "OK !";
		int s2 = write(descBrCv, msg, sizeof(msg));
	}
	close(desc);
	return 0;
}
