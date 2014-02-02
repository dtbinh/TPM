#include<iostream>
#include <stdlib.h>
#include <math.h>
#include "ARN.h"

using namespace std;

//Constructeurs**********************************//

ARN::ARN(bool (*EgaleCleUSER)(int cle1,int cle2), bool (*InferieurCleUSER)(int cle1,int cle2)){
  //creer un arn vide et on fixe les operateurs de comparaison

  nul=new noeud;
  nul->pere=NULL;
  nul->fd=NULL;
  nul->fg=NULL;
  nul->cle=-1;
  nul->coul=noir;

  racine=nul;

  EgaleCle=EgaleCleUSER;
  InferieurCle=InferieurCleUSER;
}


ARN::ARN(int val, bool (*EgaleCleUSER)(int cle1,int cle2), bool (*InferieurCleUSER)(int cle1,int cle2)){
  //creer un arn avec une racine et on fixe les operateurs de comparaison

  nul=new noeud;
  nul->pere=NULL;
  nul->fd=NULL;
  nul->fg=NULL;
  nul->cle=-1;
  nul->coul=noir;

  racine= new noeud;
  racine->pere=nul;
  racine->fd=nul;
  racine->fg=nul;
  racine->cle=val;
  racine->coul=noir;

  EgaleCle=EgaleCleUSER;
  InferieurCle=InferieurCleUSER;

}

//Destructeur************************************//

ARN::~ARN(){
  noeud* courant = racine;
  if(racine!=nul){
    while(racine->fg!=nul && racine->fd!=nul){// Tant qu'il ne reste plus que la racine
      
      while(courant->fg!=nul && courant->fd!=nul){ //On descend dans l'arbre
	if(courant->fg!=nul){
	  courant=courant->fg;}
	if(courant->fd!=nul){
	  courant=courant->fd;}
      }
      

      if(courant==courant->pere->fg){// Le noeud courant est fils gauche de son pere
	courant=courant->pere;//On remonte d'un cran
	delete courant->fg; //On efface le fils gauche
	courant->fg=nul;}
      else{ //Le noeud courant est fils droit de son pere
	courant=courant->pere;//On remonte d'un cran
	delete courant->fd; //On efface le fils droit
	courant->fd=nul;}
    }
    
    delete racine;
    delete nul;
  
  }
}

//I.O.*******************************************//

void ARN::afficheNoeud(noeud *courant, int profondeur){//Appel reccursif, la profondeur indique l'indentation necessaire.
  if(courant->fg!=nul){ 
    for(int i=0;i<profondeur;i++){
      cout << '|';
    }
    cout << "-> Fils gauche de cle = " << courant->fg->cle << " et de couleur ";
    ( courant->fg->coul == noir ) ? cout << "noir" : cout << "rouge";  
    cout << " :"<<endl;
    afficheNoeud(courant->fg,profondeur+1);
  }
  if(courant->fd!=nul){ 
    for(int i=0;i<profondeur;i++){
      cout << '|';
    }
    cout << "-> Fils droit de cle = " << courant->fd->cle <<  " et de couleur ";
    ( courant->fd->coul == noir ) ? cout << "noir" : cout << "rouge";  
    cout << " :"<<endl;
    afficheNoeud(courant->fd,profondeur+1);
  }
}

void ARN::Affichage(){ //Affichage ecran avec indentation, on affiche reccursivement.

  if(racine!=nul){
    cout<< endl;
    cout<< "Racine de cle = " << racine->cle << " et de couleur ";
    ( racine->coul == noir ) ? cout << "noir" : cout << "rouge";  
    cout << " :"<<endl;
    afficheNoeud(racine,1);
    cout << endl;
  }
  else{
    cout << "Arn vide." << endl;
  }
}


//Chercher un noeud*******************************//


noeud* ARN::chercheNoeud(int val, noeud* courant){


  if((*EgaleCle)(val,courant->cle)){
    return courant;}
  if((*InferieurCle)(courant->cle,val)){
    if(courant->fd!=nul){
      return chercheNoeud(val,courant->fd);}
    else{
      return courant;}
  }  
  else{
    if(courant->fg!=nul){
      return chercheNoeud(val,courant->fg);}
    else{
      return courant;}
  }
}

noeud* ARN::Trouve(int val){//Cherche le noeud correspondant reccursivement
  //Si la valeur n'est pas presente, retourne un noeud ou val est inserable
  //comme fils droit ou gauche
  if(racine!=nul){
    return chercheNoeud(val, racine);
  }
  else{
    return nul;
  }
}


noeud* ARN::successeur(noeud* x){//Trouve le successeur de x sous reserve que x soit non nul
  if(x->fd!=nul){//Si x a un successeur droit, on prend le min de la sous arbo droite
    x=x->fd;
    while(x->fg!=nul){
      x=x->fg;}
    return x;
  }
  while(x->pere!=nul && x==x->pere->fd){//Sinon, on retourne le premier ancetre dont x descend a gauche
    x=x->pere;
  }
  return x->pere;//Au pire, si x est la racine, l'element de depart etait le plus grand et on retourne nul
}


noeud* ARN::predecesseur(noeud* x){//Trouve le predecesseur de x sous reserve que x soit non nul
  if(x->fg!=nul){//Si x a un successeur gauche, on prend le max de la sous arbo gauche
    x=x->fg;
    while(x->fd!=nul){
      x=x->fd;}
    return x;
  }
  while(x->pere!=nul && x==x->pere->fg){//Sinon, on retourne le premier ancetre dont x descend a droite
    x=x->pere;
  }
  return x->pere;//Au pire, si x est la racine, l'element de depart etait le plus grand et on retourne nul
}





//Inserer un noeud********************************//

void ARN::RotationGauche(noeud* x){//pivote x et son fils droit y sous l'hypothese que celui-ci soit non null

  noeud* y=x->fd;

  x->fd=y->fg; //Le fils gauche de y devient fils droit de x
  if(y->fg!=nul){
    y->fg->pere=x;
  }

  y->pere=x->pere;//On met a jour pere dans y
  if(x->pere==nul){//Cas ou x est racine
    racine=y;}
  else{//Sinon on met a jour le nouveau pere de y
    if(x==x->pere->fg){
      x->pere->fg=y;}
    else{x->pere->fd=y;}
  }
  
  y->fg=x;//Nouveau lien entre x et y
  x->pere=y;
}


void ARN::RotationDroite(noeud* x){//pivote x et son fils gauche y sous l'hypothese que celui-ci soit non null

  noeud* y=x->fg;

  x->fg=y->fd; //Le fils droit de y devient fils gauche de x
  if(y->fd!=nul){
    y->fd->pere=x;
  }

  y->pere=x->pere;//On met a jour pere dans y
  if(x->pere==nul){//Cas ou x est racine
    racine=y;}
  else{//Sinon on met a jour le nouveau pere de y
    if(x==x->pere->fg){
      x->pere->fg=y;}
    else{x->pere->fd=y;}
  }
 
  y->fd=x;//Nouveau lien entre x et y
  x->pere=y;
}


noeud* ARN::insertionClassique(int val){
  //Insere comme dans un arbre de recherche classique
 
  noeud* point_insertion=Trouve(val);
  if((*EgaleCle)(point_insertion->cle,val)){
    cout << "Valeur deja existante..."<<endl;
    return nul;}
  else{
    noeud *nouveau= new noeud;
    nouveau->pere=point_insertion;
    if((*InferieurCle)(point_insertion->cle,val)){
      point_insertion->fd=nouveau;}
    else{
      point_insertion->fg=nouveau;
    }
    nouveau->fd=nul;
    nouveau->fg=nul;
    nouveau->cle=val;
    nouveau->coul=rouge;
    return nouveau;
  }

}

void ARN::Insere(int val){
  //On insere un noeud de cle val et reequilibre l'arbre.

  if(racine!=nul){
    noeud *x=insertionClassique(val); //x est insere comme feuille rouge


    if(x!=nul){//Si le noeud n'existait pas deja, on reequilibre l'arbre

      while(x->pere!=nul && x->pere->coul==rouge && x->pere->pere!=nul){ //Tant que le pere existe, est rouge, et que le grand-pere existe:
	if(x->pere==x->pere->pere->fg){//Si le pere de x est un fils gauche


	  noeud *y=x->pere->pere->fd;// y est l'oncle de x
	  if(y!=nul && y->coul==rouge){ //x, son pere et son oncle sont rouges, on met son grand-pere a rouge et on remonte dans l'arbre
	    x->pere->coul=noir;
	    y->coul=noir;
	    x->pere->pere->coul=rouge;
	    x=x->pere->pere;
	  }
	  else{//l'oncle de x est noir ou n'existe pas
	    if(x==x->pere->fd){//Si x est fils droit
	      x=x->pere;
	      this->RotationGauche(x); //Maintenant, le noeud considere est fils gauche
	    }
	    x->pere->coul=noir;//On met le pere a noir, le grand-pere a rouge et on effectue une rot. droite sur le grand-pere, et c'est bon (le 
	    //nouvel ancetre est le pere qui est noir).
	    x->pere->pere->coul=rouge;
	    this->RotationDroite(x->pere->pere);
	  }
	}


	else{ // Si le pere de x est un fils droit (on fait de meme)

	
	  noeud *y=x->pere->pere->fg;// y est l'oncle de x
	  if(y!=nul && y->coul==rouge){ //x, son pere et son oncle sont rouges, on met son grand-pere a rouge et on remonte dans l'arbre
	    x->pere->coul=noir;
	    y->coul=noir;
	    x->pere->pere->coul=rouge;
	    x=x->pere->pere;
	  }
	  else{//l'oncle de x est noir ou n'existe pas
	    if(x==x->pere->fg){//Si x est fils gauche
	      x=x->pere;
	      this->RotationDroite(x); //Maintenant, le noeud considere est fils droit
	    }
	    x->pere->coul=noir;//On met le pere a noir, le grand-pere a rouge et on effectue une rot. gauche sur le grand-pere, et c'est bon (le 
	    //nouvel ancetre est le pere qui est noir).
	    x->pere->pere->coul=rouge;
	    this->RotationGauche(x->pere->pere);
	  }
	}
      }
      racine->coul=noir; //on s'est peut etre arrete avec un fils de la racine rouge.
    }
  }
  else{
    racine= new noeud;
    racine->pere=nul;
    racine->fd=nul;
    racine->fg=nul;
    racine->cle=val;
    racine->coul=noir;
  }
}
  
//Supprimer un noeud *****************************//


void ARN::SupprimerCorrection(noeud* x){
  while(x!=racine && x->coul==noir){//x a un pere et sa couleur est noire

    if(x->pere->fg==x){//x est fils gauche
      noeud* w=x->pere->fd;//Le frere de x, non nul car le nbre de noirs
                           //sous w est > celui sous x

      if(w->coul==rouge){//cas 1, on se ramene a un w noir
	w->coul=noir;
	x->pere->coul=rouge;
	RotationGauche(x->pere);
	w=x->pere->fd;}

      if(w->fg->coul==noir && w->fd->coul==noir){//pour les ms raisons w a 2 fils
	w->coul=rouge; //cas 2
	x=x->pere;}//on remonte d'un cran, si x est rouge on arete la boucle et on le mettra noir
      else{//Au moins un des 2 fils de w est rouge
	if(w->fd->coul==noir){//cas 3, on se ramene a un fils droit rouge
	  w->fg->coul=noir;
	  w->coul=rouge;
	  RotationDroite(w);
	  w=x->pere->fd;}

	w->coul=x->pere->coul;//Cas 4, le fils droit de w est rouge
	x->pere->coul=noir;//on reequilibre la branche deficiente
	w->fd->coul=noir;
	RotationGauche(x->pere);
	x=racine;//on remet la racine a noir
      }
    }else{//x est fils droit
      noeud* w=x->pere->fg;//Le frere de x, non nul car le nbre de noirs
                           //sous w est > celui sous x

      if(w->coul==rouge){//cas 1, on se ramene a un w noir
	w->coul=noir;
	x->pere->coul=rouge;
	RotationDroite(x->pere);
	w=x->pere->fg;}

      if(w->fg->coul==noir && w->fd->coul==noir){//pour les ms raisons w a 2 fils
	w->coul=rouge; //cas 2
	x=x->pere;}//on remonte d'un cran, si x est rouge on arete la boucle et on le mettra noir
      else{//Au moins un des 2 fils de w est rouge
	if(w->fg->coul==noir){//cas 3, on se ramene a un fils gauche rouge
	  w->fd->coul=noir;
	  w->coul=rouge;
	  RotationGauche(w);
	  w=x->pere->fg;}

	w->coul=x->pere->coul;//Cas 4, le fils gauche de w est rouge
	x->pere->coul=noir;//on reequilibre la branche deficiente
	w->fg->coul=noir;
	RotationDroite(x->pere);
	x=racine;//on remet la racine a noir
      }


    }
  }
  x->coul=noir;
}


void ARN::Supprime(int val){
 
  noeud*z = Trouve(val);
  if(!(*EgaleCle)(z->cle,val)){
    return;
  }

  noeud* y; //Un noeud qu'on va detacher de l'arbre
  if(z->fg==nul || z->fd==nul){
    y=z;}
  else{y=successeur(z);}//Normalement, ici, z n'est pas le max, car sinon il lui manque un fils.
  //On est sur que y n'est pas nul

  noeud* x=nul; //x prend la valeur du fils de y non null (si il existe)

  if(y->fg!=nul){
    x=y->fg;}
  else{x=y->fd;}

  x->pere=y->pere;//On mets a jour le pere dans x, marche meme si x=nul

  if(y->pere==nul)//Si y etait racine
    {if(x==nul){//c'est le cas ou il n'y a plus qu'un seul sommet dans l'arn
      delete z;
      racine=nul;
      return;
    }else{//La nouvelle racine est le fils de y
      racine=x;}
    }else{//Si y n'etait pas racine
      if(y==y->pere->fg){//le pere de y devient le pere de x
	y->pere->fg=x;}
      else{
	y->pere->fd=x;
      }
    }
  if(y!=z){//si le noeud supprime n'est pas z
    z->cle=y->cle;
  }
  if(y->coul==noir){//On a supprimer un sommet noir, les hauteurs noires ont changees, il faut reequilibrer
    SupprimerCorrection(x);
     }
  delete y;
}


//Predecesseur et Successeur*************************************//
//Retournent -1 comme cle si min (pour Predecesseur) ou max (pour
// Successeur)


int ARN::Successeur(int val){
  noeud* x=successeur(Trouve(val));
  if(x==nul){return -1;}
  else{
    return x->cle;
  }
}


int ARN::Predecesseur(int val){
  noeud* x=predecesseur(Trouve(val));
  if(x==nul){return -1;}
  else{
    return x->cle;
  }  

}


