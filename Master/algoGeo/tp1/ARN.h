
#define rouge true
#define noir false


//------------------------------
// Noeud d'une arn
//---------------- --------------

  typedef struct noeud
  {
    struct noeud* pere;
    struct noeud* fd;
    struct noeud* fg;
    int cle;
    bool coul;
  } noeud;


//------------------------------
// La classe arn
//------------------------------


class ARN{


private :
 noeud * racine; //La racine
 noeud * nul; //Le noeud nul, dont chaque feuille est le pere et qui estde couleur noir (pratique pour les algos)

 bool (*EgaleCle)(int cle1,int cle2);//Le pointeur sur l operateur d egalite
 bool (*InferieurCle)(int cle1,int cle2);//Le pointeur sur l operateur de comparaison


 void afficheNoeud(noeud *courant, int profondeur);
 noeud* chercheNoeud(int val, noeud* courant);
 noeud* Trouve(int val);
 noeud* successeur(noeud* x);
 noeud* predecesseur(noeud* x);
 noeud* insertionClassique(int val); 
 void RotationDroite(noeud* x);
 void RotationGauche(noeud* x);
 void SupprimerCorrection(noeud* x);


public : 

 ARN(bool (*EgaleCleUSER)(int cle1,int cle2), bool (*InferieurCleUSER)(int cle1,int cle2));
             //Constructeur d'un arn vide
             //On specifie les fonctions de comparaison qui doivent etre implementees
             //par ailleurs.

 ARN(int val, bool (*EgaleCleUSER)(int cle1,int cle2), bool (*InferieurCleUSER)(int cle1,int cle2)); 
             //Constructeur d'un arn en precisant la cle de la racine
             //On specifie les fonctions de comparaison qui doivent etre implementees
             //par ailleurs.

 ~ARN(); //Destructeur

 //Entrees-Sorties
 void Affichage();


 //Fonctions de base
 
 void Insere(int val);
 void Supprime(int val);
 int Successeur(int val);
 int Predecesseur(int val);

};
