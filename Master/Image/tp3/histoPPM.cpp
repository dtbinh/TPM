#include "ImageBase.h"
#include <stdio.h>

int main(int argc, char **argv)
{
	///////////////////////////////////////// Exemple d'un seuillage d'image
	char cNomImgLue[250];
  
	if(argc != 2) 
	{
		printf("Usage: ImageIn.ppm\n"); 
		return 1;
	}
	sscanf (argv[1],"%s",cNomImgLue);

	
	//ImageBase imIn, imOut;
	ImageBase imIn;
	imIn.load(cNomImgLue);

	//ImageBase imG(imIn.getWidth(), imIn.getHeight(), imIn.getColor());

	for(int i = 0; i < 256; ++i)
	{
		int occurenceR = 0;
		int occurenceG = 0;
		int occurenceB = 0;
		for(int y = 0; y < imIn.getHeight(); ++y)
		{
			for(int x = 0; x < imIn.getWidth(); ++x)
			{
				if(imIn[y*3][x*3+0] == i) // R
					occurenceR++;
				if(imIn[y*3][x*3+1] == i) // G
					occurenceG++;
				if(imIn[y*3][x*3+2] == i) // B
					occurenceB++;
			}
		}
		printf("%d %d %d %d\n", i, occurenceR, occurenceG, occurenceB);
	}

	return 0;
}
