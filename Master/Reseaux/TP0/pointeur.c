#include <stdio.h>
#include <stdlib.h>

int* extract(int *t, int n, int a, int b)
{
    int compteur = 0;
    for(int i = 0; i < n; i++)
    {
        if(t[i] >= a && t[i] <= b)
            compteur++;
    }
    int *tab = (int*) malloc((sizeof(int)) * compteur);
    //int tab[compteur];
    int j = 0;
    for(int i = 0; i < n; i++)
    {
        if(t[i] >= a && t[i] <= b)
        {
            tab[j] = t[i];
            j++;
        }
    }
    return tab;
}



int somme(int *t, int taille)
{
    if(taille == 1)
        return t[taille];
    else
    {
        return t[taille - 1] + somme(t, taille - 1);
    }
}


int main()
{
    int a = 10;
    int b = 25;
    int *p = &b;
    int *pp = &a;

    printf(" 1 -- %d \n", *(&(*(*(&p)))));
    printf(" 2 -- %d \n", *(p - 1));
    printf(" 3 -- %d \n", *(*(&p) - 1));
    printf(" 4 -- %d \n", *(*(&pp)+1));
    printf(" 5 -- %d \n", *(&(*(*(&p))) - 1));

    printf(" ---------- \n");

    int t[] = {1, 3, 5, 7, 9};

    int *tab = extract(t, 5, 2, 5);

    for(int i = 0; i < 2; i++)
        printf(" %d - ", tab[i]);
    printf("\n");

    int taille = 0;
    printf("entrer la taille du tableau\n");
    scanf("%d", &taille);
    int tableau[taille];

    for(int i = 0; i < taille; i++)
    {
        printf("entrer une valeur\n");
        scanf("%d", &(tableau[i]));
    }

    printf("\n");

    for(int i = 0; i < taille; i++)
        printf(" %d  ", tableau[i]);

    printf("\nla somme des elements du tableau est : %d \n", somme(tableau, taille));


    free(tab);
    return 0;
}
