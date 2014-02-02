#include "ImageBase.h"
#include <stdio.h>

int main(int argc, char **argv)
{
	///////////////////////////////////////// Exemple d'un seuillage d'image
	char cNomImgLue[250], cNomImgEcrite[250];
  
	if(argc != 3) 
	{
		printf("Usage: ImageIn.pgm ImageOut.pgm\n"); 
		return 1;
	}
	sscanf (argv[1],"%s",cNomImgLue);
	sscanf (argv[2],"%s",cNomImgEcrite);

	
	//ImageBase imIn, imOut;
	ImageBase imIn;
	imIn.load(cNomImgLue);

	//ImageBase imG(imIn.getWidth(), imIn.getHeight(), imIn.getColor());
	int histo[256] = {0};

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
		histo[i] = occurence;
		//printf("%d %d\n", i, occurence);
	}

	int max = 0;
	for(int i = 0; i < 256; i++)
	{
		if(histo[i] > max)
			max = histo[i];
		//printf("%d %d\n", i, histo[i]);
	}
	printf("max = %d\n", max);
	int seuil = max * 0.01;

	//suppression des petites valeurs
	for(int i = 0; i < 256; ++i)
	{
		if(histo[i] < seuil)
			histo[i] = 0;
		printf("%d %d\n", i, histo[i]);
	}

	//calcul Amin et Amax
	int A0 = 0;
	int A1 = 0;
	for(int i = 0; i < 256; i++)
	{
		if(histo[i] > 0)
		{
			A0 = i;
			break;
		}
	}
	for(int i = 255; i >= 0; i--)
	{
		if(histo[i] > 0)
		{
			A1 = i;
			break;
		}
	}
	printf("a0 = %d, a1 = %d\n", A0, A1);

	int alfa;
	int beta;
	int Amin = 0;
	int Amax = 255;

	alfa = ((Amin * A1) - (Amax * A0)) / (A1 - A0);
	beta = (Amax - Amin) / (A1 - A0);

	printf("alfa = %d, beta = %d\n", alfa, beta);

	ImageBase imOut(imIn.getWidth(), imIn.getHeight(), imIn.getColor());

	for(int x = 0; x < imIn.getHeight(); ++x)
		for(int y = 0; y < imIn.getWidth(); ++y)
		{
			imOut[x][y] = alfa + (beta * imIn[x][y]);
		}

	imOut.save(cNomImgEcrite);

	return 0;
}
