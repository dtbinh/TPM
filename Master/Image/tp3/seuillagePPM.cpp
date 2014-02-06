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

	ImageBase imOut(imIn.getWidth(), imIn.getHeight(), imIn.getColor());

	
	for(int x = 0; x < imIn.getHeight(); ++x)
			for(int y = 0; y < imIn.getWidth(); ++y)
			{
				if (imIn[x*3][y*3+0] < sMinR) //r
					imOut[x*3][y*3+0] = sMinR;
				else if (imIn[x*3][y*3+0] > sMaxR)
					imOut[x*3][y*3+0] = sMaxR;
				else imOut[x*3][y*3+0]=imIn[x*3][y*3+0];
				
				if(imIn[x*3][y*3+1] < sMinG ) // v
					imOut[x*3][y*3+1] = sMinG;
				else if(imIn[x*3][y*3+1] > sMaxG)
					imOut[x*3][y*3+1]=sMaxG;
				else imOut[x*3][y*3+1]=imIn[x*3][y*3+1];				

				if(imIn[x*3][y*3+2] < sMinB)// b
					imOut[x*3][y*3+2] = sMinB;
				else if(imIn[x*3][y*3+2] > sMaxB)
					imOut[x*3][y*3+2]=sMaxB;
				else imOut[x*3][y*3+2]=imIn[x*3][y*3+2];
			}

	imOut.save(cNomImgEcrite);

	return 0;
}
