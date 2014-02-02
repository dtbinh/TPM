#include "ImageBase.h"
#include <stdio.h>

int main(int argc, char **argv)
{
	///////////////////////////////////////// Exemple d'un seuillage d'image
	char cNomImgLue[250], cNomImgEcrite[250];
	int sMaxR, sMinR;
	int sMaxG, sMinG;
	int sMaxB, sMinB;
  
	if(argc != 9) 
	{
		printf("Usage: ImageIn.ppm Smin Smax\n"); 
		return 1;
	}
	sscanf (argv[1],"%s",cNomImgLue);
	sscanf (argv[2],"%d",&sMinR);
	sscanf (argv[3],"%d",&sMaxR);
	sscanf (argv[4],"%d",&sMinG);
	sscanf (argv[5],"%d",&sMaxG);
	sscanf (argv[6],"%d",&sMinB);
	sscanf (argv[7],"%d",&sMaxB);
	sscanf (argv[8],"%s",cNomImgEcrite);

	
	//ImageBase imIn, imOut;
	ImageBase imIn;
	imIn.load(cNomImgLue);

	ImageBase imOu(imIn.getWidth(), imIn.getHeight(), imIn.getColor());

	
	for(int y = 0; y < imIn.getHeight(); ++y)
	{
		for(int x = 0; x < imIn.getWidth(); ++x)
		{
			imOut[y*3][x*3+0] = imIn[y*3][x*3+0];
			imOut[y*3][x*3+1] = imIn[y*3][x*3+1];
			imOut[y*3][x*3+2] = imIn[y*3][x*3+2];

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
				imOut[y*3][x*3+2] = sMinB;
		}
	}

	imOut.save(cNomImgEcrite);

	return 0;
}
