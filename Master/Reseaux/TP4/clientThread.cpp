//client
#include <iostream>
#include <cstdio>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include "sock.h"
#include "sockdist.h"
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>
#include <pwd.h>

using namespace std;

int main(int argc, char *argv[])
{
	//creation de la boite reseau locale
	Sock brClient(SOCK_STREAM, 0);
	int descBrCli;
	if(brClient.good())
		descBrCli = brClient.getsDesc();
	else
	{
		cout << "Pb BR" << endl;
		exit(1);
	}

	SockDist brPublic(argv[1], atoi(argv[2]));
	struct sockaddr_in* adrBrPub = brPublic.getAdrDist();
	int lg = sizeof(struct sockaddr_in);

	int resCo = connect(descBrCli, (struct sockaddr*)adrBrPub, lg);
	while(true)
	{
		//boucle message a envoyer
		char buffer[256];
		cout << "entrer un message a envoyer au serveur:" << endl;
		fgets(buffer, sizeof(buffer), stdin);

		if(resCo != -1)
		{
			int s = write(descBrCli, buffer, sizeof(buffer));
			if(s)
				cout << "message envoyé!" << endl;
		}

		int s2 = read(descBrCli, buffer, sizeof(buffer));
		if(s2 > 0)
		{
			cout << "message reçu: " << buffer << endl;
		}
	}
	
	close(descBrCli);
	return 0;
}