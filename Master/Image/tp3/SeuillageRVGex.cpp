#include "ImageBase.h"
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <iomanip>
#include <cstdlib>
using namespace std;

int main(int argc, char **argv)
{

	char cNomImgLue[250], cNomImgSeuillee[250];
  		int SminR;
		int SmaxR;
		int SminV;
		int SmaxV;
		int SminB;
		int SmaxB;
	  
		
	if (argc < 9 )// a voir!
	{
		printf("Usage: ImageIn.pgm ImageOut.pgm \n");
		return 1;
	}
	sscanf (argv[1],"%s",cNomImgLue) ;
	sscanf (argv[2],"%s",cNomImgSeuillee);
	
	sscanf (argv[3],"%d",&SminR);
	sscanf (argv[4],"%d",&SmaxR);
	sscanf (argv[5],"%d",&SminV);
	sscanf (argv[6],"%d",&SmaxV);
	sscanf (argv[7],"%d",&SminB);
	sscanf (argv[8],"%d",&SmaxB);
	
	//ImageBase imIn, imOut;
	ImageBase imIn;
	imIn.load(cNomImgLue);

	if(imIn.getColor()==true){

///////////////////////////////IMAGE SEUILLEE
/////////////////seuillage et creation de la nouvelle image

		ImageBase imOut(imIn.getWidth(), imIn.getHeight(), imIn.getColor());


		for(int x = 0; x < imIn.getHeight(); ++x)
			for(int y = 0; y < imIn.getWidth(); ++y)
			{
				if (imIn[x*3][y*3+0] < SminR) //r
					imOut[x*3][y*3+0] = SminR;
				else if (imIn[x*3][y*3+0] > SmaxR)
					imOut[x*3][y*3+0] = SmaxR;
				else imOut[x*3][y*3+0]=imIn[x*3][y*3+0];
				
				if(imIn[x*3][y*3+1] < SminV ) // v
					imOut[x*3][y*3+1] = SminV;
				else if(imIn[x*3][y*3+1] > SmaxV)
					imOut[x*3][y*3+1]=SmaxV;
				else imOut[x*3][y*3+1]=imIn[x*3][y*3+1];				

				if(imIn[x*3][y*3+2] < SminB)// b
					imOut[x*3][y*3+2] = SminB;
				else if(imIn[x*3][y*3+2] > SmaxB)
					imOut[x*3][y*3+2]=SmaxB;
				else imOut[x*3][y*3+2]=imIn[x*3][y*3+2];
			}	
	
		imOut.save(cNomImgSeuillee);
		

	int tabHistoR[256];
	int tabHistoV[256];
	int tabHistoB[256];

	//////////////////////// Nouveau histogramme  de l'image seuillee
			for(int NdG=0;NdG<256;++NdG){
				tabHistoR[NdG]=0;
				tabHistoV[NdG]=0;
				tabHistoB[NdG]=0;
			}
			for(int x = 0; x < imOut.getHeight(); ++x){
					for(int y = 0; y < imOut.getWidth(); ++y)
					{
						int NdR = imOut[x*3][y*3+0];
						int NdV = imOut[x*3][y*3+1];
						int NdB = imOut[x*3][y*3+2]; 
					
						tabHistoR[NdR]++;
						tabHistoV[NdV]++;
						tabHistoB[NdB]++;
					
					}
				}


	///////////////////////creation du fichier de l'image seuillee
			ofstream fichier("histoFinalS.dat", ios::out|ios::trunc);
			if(fichier){
				for(int NdG=0;NdG<256;NdG++)
					fichier <<NdG<<"\t"<<tabHistoR[NdG]<<"\t"<<tabHistoV[NdG]<<"\t"<<tabHistoB[NdG]<<endl;
				fichier.close();
				cout<<"Appliquer l'extension RGB sur image seuillee"<<endl;
			}
			else
				cout <<"erreur à l'ouverture"<<endl;

		}

	
	
	return 0;
}
