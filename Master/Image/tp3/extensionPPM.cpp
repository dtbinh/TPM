#include "ImageBase.h"
#include <stdio.h>

int main(int argc, char **argv)
{
	///////////////////////////////////////// Exemple d'un seuillage d'image
	char cNomImgLue[250], cNomImgEcrite[250];
	int sMaxR, sMinR;
	int sMaxG, sMinG;
	int sMaxB, sMinB;
  
	if(argc != 3) 
	{
		printf("Usage: ImageIn.ppm Smin Smax\n"); 
		return 1;
	}
	sscanf (argv[1],"%s",cNomImgLue);
	/*sscanf (argv[2],"%d",&sMinR);
	sscanf (argv[3],"%d",&sMaxR);
	sscanf (argv[4],"%d",&sMinG);
	sscanf (argv[5],"%d",&sMaxG);
	sscanf (argv[6],"%d",&sMinB);
	sscanf (argv[7],"%d",&sMaxB);*/
	sscanf (argv[2],"%s",cNomImgEcrite);

	
	//ImageBase imIn, imOut;
	ImageBase imIn;
	imIn.load(cNomImgLue);

	ImageBase imOut(imIn.getWidth(), imIn.getHeight(), imIn.getColor());

	
	for(int y = 0; y < imIn.getHeight(); ++y)
	{
		for(int x = 0; x < imIn.getWidth(); ++x)
		{
			imOut[y*3][x*3+0] = imIn[y*3][x*3+0];
			imOut[y*3][x*3+1] = imIn[y*3][x*3+1];
			imOut[y*3][x*3+2] = imIn[y*3][x*3+2];
/*
			if(imIn[y*3][x*3+0] > sMaxR) // R
				imOut[y*3][x*3+0] = sMaxR;
			if(imIn[y*3][x*3+0] < sMinR) // R
				imOut[y*3][x*3+0] = sMinR;

			if(imIn[y*3][x*3+1] > sMaxG) // G
				imOut[y*3][x*3+1] = sMaxG;
			if(imIn[y*3][x*3+1] < sMinG) // G
				imOut[y*3][x*3+1] = sMinG;

			if(imIn[y*3][x*3+2] > sMaxB) // B
				imOut[y*3][x*3+2] = sMaxB;
			if(imIn[y*3][x*3+2] < sMinB) // B
				imOut[y*3][x*3+2] = sMinB;*/
		}
	}


	//===============================extension=============================
	int histoR[256] = {0};
	int histoG[256] = {0};
	int histoB[256] = {0};

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
		histoB[i] = occurenceB;
		histoG[i] = occurenceG;
		histoR[i] = occurenceR;
		//printf("%d %d %d %d\n", i, occurenceR, occurenceG, occurenceB);
	}



	int maxR = 0;
	int maxG = 0;
	int maxB = 0;
	for(int i = 0; i < 256; i++)
	{
		if(histoR[i] > maxR)
			maxR = histoR[i];
		if(histoG[i] > maxG)
			maxG = histoG[i];
		if(histoB[i] > maxB)
			maxB = histoB[i];
		//printf("%d %d\n", i, histo[i]);
	}
	//printf("max = %d\n", maxR);
	int seuilR = maxR * 0.01;
	int seuilG = maxG * 0.01;
	int seuilB = maxB * 0.01;

	//suppression des petites valeurs
	for(int i = 0; i < 256; ++i)
	{
		if(histoR[i] < seuilR)
			histoR[i] = 0;
		if(histoG[i] < seuilG)
			histoG[i] = 0;
		if(histoB[i] < seuilB)
			histoB[i] = 0;
		//printf("%d %d\n", i, histoR[i]);
	}

	//calcul Amin et Amax
	int A0R = 0;
	int A1R = 0;
	int A0G = 0;
	int A1G = 0;
	int A0B = 0;
	int A1B = 0;
	for(int i = 0; i < 256; i++)
	{
		if(histoR[i] > 0)
		{
			A0R = i;
			break;
		}
	}
	for(int i = 0; i < 256; i++)
	{
		if(histoG[i] > 0)
		{
			A0G = i;
			break;
		}
	}
	for(int i = 0; i < 256; i++)
	{
		if(histoB[i] > 0)
		{
			A0B = i;
			break;
		}
	}

	for(int i = 255; i >= 0; i--)
	{
		if(histoR[i] > 0)
		{
			A1R = i;
			break;
		}
	}
	for(int i = 255; i >= 0; i--)
	{
		if(histoG[i] > 0)
		{
			A1G = i;
			break;
		}
	}
	for(int i = 255; i >= 0; i--)
	{
		if(histoB[i] > 0)
		{
			A1B = i;
			break;
		}
	}
	//printf("a0 = %d, a1 = %d\n", A0, A1);

	double alfaR;
	double betaR;
	double alfaG;
	double betaG;
	double alfaB;
	double betaB;
	double Amin = 0;
	double Amax = 255;

	alfaR = (double) ((Amin * A1R) - (Amax * A0R)) / (double) (A1R - A0R);
	betaR = (double) (Amax - Amin) / (A1R - A0R);
	alfaG = (double) ((Amin * A1G) - (Amax * A0G)) / (double) (A1G - A0G);
	betaG = (double) (Amax - Amin) / (A1G - A0G);
	alfaB = (double) ((Amin * A1B) - (Amax * A0B)) / (double) (A1B - A0B);
	betaB = (double) (Amax - Amin) / (A1B - A0B);

	printf("alfaR = %f, betaR = %f\n", alfaR, betaR);
	printf("alfaG = %f, betaG = %f\n", alfaG, betaG);
	printf("alfaB = %f, betaB = %f\n", alfaB, betaB);

	for(int x = 0; x < imIn.getHeight(); ++x)
		for(int y = 0; y < imIn.getWidth(); ++y)
		{
			imOut[x*3][y*3+0] = alfaR + (betaR * imIn[x*3][y*3+0]);
			imOut[x*3][y*3+1] = alfaG + (betaG * imIn[x*3][y*3+1]);
			imOut[x*3][y*3+2] = alfaB + (betaB * imIn[x*3][y*3+2]);
		}

	imOut.save(cNomImgEcrite);

	return 0;
}
