#include "ImageBase.h"
#include <stdio.h>

int main(int argc, char **argv)
{
	///////////////////////////////////////// Exemple d'un seuillage d'image
	char cNomImgLue[250];
	char info;
	int indice;
  
	if(argc != 4) 
	{
		printf("Usage: ImageIn.pgm C(ou L) Indice \n"); 
		return 1;
	}
	sscanf (argv[1],"%s",cNomImgLue);
	sscanf (argv[2],"%c",&info);
	sscanf (argv[3],"%d",&indice);

	
	//ImageBase imIn, imOut;
	ImageBase imIn;
	imIn.load(cNomImgLue);

	//ImageBase imG(imIn.getWidth(), imIn.getHeight(), imIn.getColor());

	if(info == 'C')
	{
		for(int x = 0; x < imIn.getHeight(); ++x)
		{
			printf("%d %d\n", x, imIn[x][indice]);
		}
	}
	else if(info == 'L')
	{
		for(int y = 0; y < imIn.getWidth(); ++y)
		{
			printf("%d %d\n", y, imIn[indice][y]);
		}
	}
	else
	{
		printf("Usage: ImageIn.pgm C(ou L) Indice \n"); 
		return 1;
	}

	return 0;
}
