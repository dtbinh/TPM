#include "ImageBase.h"
#include <stdio.h>

int main(int argc, char **argv)
{
	///////////////////////////////////////// Exemple d'un seuillage d'image
	char cNomImgLue[250];
  
	if(argc != 2) 
	{
		printf("Usage: ImageIn.pgm\n"); 
		return 1;
	}
	sscanf (argv[1],"%s",cNomImgLue);

	
	//ImageBase imIn, imOut;
	ImageBase imIn;
	imIn.load(cNomImgLue);

	//ImageBase imG(imIn.getWidth(), imIn.getHeight(), imIn.getColor());

	for(int i = 0; i < 256; ++i)
	{
		int occurence = 0;
		for(int x = 0; x < imIn.getHeight(); ++x)
		{
			for(int y = 0; y < imIn.getWidth(); ++y)
			{
				//seuillage 2 partie
				/*
				if (imIn[x][y] < S) 
					imOut[x][y] = 0;
				else imOut[x][y] = 255;
				*/
				if(imIn[x][y] == i)
					occurence++;
			}
		}
		printf("%d %d\n", i, occurence);
	}

	return 0;
}
