#include <cstdlib>
#include <iostream>
#include <string.h>

using namespace std;
#include "Wave.hpp"
#include <cmath>

char fileName[200]; // Fichier wave

//==============================================================================
void printUsage(char* name) {

  cout << "\nUsage : "
       <<name
       <<" fichier.wav\n";
  exit(-1);
}

//==============================================================================
void processOptionsInLine(int argc, char** argv){  
  if (argc != 2) {
    printUsage(argv[0]);
  }
  //RECOPIE DU NOM DE L'IMAGE DANS LA VARIABLE seqName
  strcpy(fileName, argv[1]);  
}

int main(int argc, char *argv[]) {

  processOptionsInLine(argc, argv);

  //cout<<"a compléter ...\n";


  unsigned char data8[44100*6];
  int size = 44100*6;
  for(int i = 0; i < size; i++)
  {
    data8[i] = (sin(440*i)+1)*127;
  }

  Wave nouveauSon = Wave(data8, size, 1, 44100);
  nouveauSon.write("sondemalade.wav");
}

