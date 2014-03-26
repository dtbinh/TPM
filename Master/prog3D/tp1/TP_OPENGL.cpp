///////////////////////////////////////////////////////////////////////////////
// Imagina
// ----------------------------------------------------------------------------
// IN - Synthèse d'images - Modélisation géométrique
// Auteur : Gilles Gesquière
// ----------------------------------------------------------------------------
// Base du TP 1
// programme permettant de créer des formes de bases.
// La forme représentée ici est un polygone blanc dessiné sur un fond rouge
///////////////////////////////////////////////////////////////////////////////  

#include <stdio.h>      
#include <stdlib.h>     
#include <math.h>
#include <iostream>
#include <vector>


/* Dans les salles de TP, vous avez généralement accès aux glut dans C:\Dev. Si ce n'est pas le cas, téléchargez les .h .lib ...
Vous pouvez ensuite y faire référence en spécifiant le chemin dans visual. Vous utiliserez alors #include <glut.h>. 
Si vous mettez glut dans le répertoire courant, on aura alors #include "glut.h" 
*/

#include <GL/glut.h> 
#include "Vector.h"
#include "Point.h"
#include "Cube.h"

using namespace std;

// Définition de la taille de la fenêtre
#define WIDTH  480
#define HEIGHT 480

#define MINORTHO -50.0
#define MAXORTHO 50.0

// Définition de la couleur de la fenêtre
#define RED   0
#define GREEN 0
#define BLUE  0
#define ALPHA 1


// Touche echap (Esc) permet de sortir du programme
#define KEY_ESC 27

//touche pour augmenter ou diminuer le nombre de point de U
#define KEY_D 100
#define KEY_Q 113
#define KEY_L 108
#define KEY_K 107
#define KEY_PLUS 61
#define KEY_MOINS 45
long nbU = 3;
const double PI = 3.141592653589793238463;

long nbControlPoint = 4;
vector<Point> tabControlPoint;
vector<Point> tabControlPoint2;

vector<Point> northPoints;
vector<Point> southPoints;
float angleCam = 0;

int nbParallele = 8;
int nbMeridien = 8;


// Entêtes de fonctions
void init_scene();
void render_scene();
GLvoid initGL();
GLvoid window_display();
GLvoid window_reshape(GLsizei width, GLsizei height); 
GLvoid window_key(unsigned char key, int x, int y);


void drawAxes();
void drawPoint(const Point& p);
void drawVector(const Point& p, const Vector& v);
void drawSegment(const Point& p1, const Point& p2);
void drawCurve(const vector<Point> tabPointsOfCurve);
void drawCurve(const Point* tabPointsOfCurve, const long nbPoints);
void movePoint(int x, int y);
double pixelToOrthoX(int x);
double pixelToOrthoY(int y);

void hermiteCubicCurve(Point p0, Point p1, Vector v0, Vector v1, long nbU, Point* tab);

void bezierCurveByBernstein(Point* tabControlPoint, long nbControlPoint, long nbU, Point* tab);
int fact(int n);

void bezierCurveByCasteljau(vector<Point> tabControlPoint, long nbControlPoint, long nbU, Point* tab);
Point casteljauRec(int k, int i, double u, vector<Point> tabControlPoint);
void surfaceReglee(Point* tab, Point* tab2, long nbU);
Point segmentPoint(Point p1, Point p2, int i, int nbU);

void facettisationCylindre(double rayon, double hauteur, int nbMeridien);
void drawCylinder(vector<Point> northPoints, vector<Point> southPoints);

void facettisationCone(float rayon, float hauteur, Point sommet, int nbMeridien);
void drawCone(Point sommet, vector<Point> basePoints);

void facettisationSphere(float rayon, int nbMeridien, int nbParallele);

void drawCube(Point center, float size);
void drawCube(Point* v, float size, int len);


int main(int argc, char **argv) 
{
  // initialisation  des paramètres de GLUT en fonction
  // des arguments sur la ligne de commande
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_RGBA);

  // définition et création de la fenêtre graphique, ainsi que son titre
  glutInitWindowSize(WIDTH, HEIGHT);
  glutInitWindowPosition(0, 0);
  glutCreateWindow("Premier exemple : carré");

  // initialisation de OpenGL et de la scène
  initGL();  
  init_scene();

  // choix des procédures de callback pour 
  // le tracé graphique
  glutDisplayFunc(&window_display);
  // le redimensionnement de la fenêtre
  glutReshapeFunc(&window_reshape);
  // la gestion des événements clavier
  glutKeyboardFunc(&window_key);
  glutMotionFunc(movePoint);

  // la boucle prinicipale de gestion des événements utilisateur
  glutMainLoop();

  return 1;
}

// initialisation du fond de la fenêtre graphique : noir opaque
GLvoid initGL() 
{
  glClearColor(RED, GREEN, BLUE, ALPHA);        
}

// Initialisation de la scene. Peut servir à stocker des variables de votre programme
// à initialiser
void init_scene()
{
}

// fonction de call-back pour l´affichage dans la fenêtre

GLvoid window_display()
{
  glClear(GL_COLOR_BUFFER_BIT);
  glLoadIdentity();

  // C'est l'endroit où l'on peut dessiner. On peut aussi faire appel
  // à une fonction (render_scene() ici) qui contient les informations 
  // que l'on veut dessiner
  render_scene();

  // trace la scène grapnique qui vient juste d'être définie
  glFlush();
}

// fonction de call-back pour le redimensionnement de la fenêtre

GLvoid window_reshape(GLsizei width, GLsizei height)
{  
  glViewport(0, 0, width, height);

  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  // ici, vous verrez pendant le cours sur les projections qu'en modifiant les valeurs, il est
  // possible de changer la taille de l'objet dans la fenêtre. Augmentez ces valeurs si l'objet est 
  // de trop grosse taille par rapport à la fenêtre.
  //glOrtho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0);
  glOrtho(MINORTHO, MAXORTHO, MINORTHO, MAXORTHO, MINORTHO, MAXORTHO);

  // toutes les transformations suivantes s´appliquent au modèle de vue 
  glMatrixMode(GL_MODELVIEW);
}

// fonction de call-back pour la gestion des événements clavier

GLvoid window_key(unsigned char key, int x, int y) 
{  
  switch (key) {    
  case KEY_ESC:  
    exit(1);                    
    break; 
  case KEY_D:
    nbU++;
    window_display();
    break;
  case KEY_Q:
    if(nbU > 3)
      nbU--;
    window_display();
    break;
  case KEY_L:
    if(angleCam < 360)
      angleCam++;
    window_display();
    break;
  case KEY_K:
    if(angleCam > 0)
      angleCam--;
    window_display();
    break;
  case KEY_PLUS:
    nbParallele++;
    nbMeridien++;
    window_display();
    break;
  case KEY_MOINS:
    if(nbParallele > 4)
    {
      nbParallele--;
      nbMeridien--;
    }
    window_display();
    break;
  default:
    printf ("La touche %d n´est pas active.\n", key);
    break;
  }     
}



//////////////////////////////////////////////////////////////////////////////////////////>>>>>>>>>>>>>>>
// Fonction que vous allez modifier afin de dessiner
/////////////////////////////////////////////////////////////////////////////////////////>>>>>>>>>>>>>>>>
void render_scene()
{
  glRotated(angleCam, 0, 1, 0);
  //permet de dessiner des polygone vide
  glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
  //Définition de la couleur
  glColor3f(1.0, 1.0, 1.0);

  //  Nous créons ici un polygone. Nous pourrions aussi créer un triangle ou des lignes. Voir ci-dessous les parties 
  // en commentaires (il faut commenter le bloc qui ne vous intéresse pas et décommenter celui que vous voulez tester.
  tabControlPoint.clear();
  tabControlPoint.push_back(Point(-1.0, 0.0, 0.0));
  tabControlPoint.push_back(Point(-0.5, 0.7, 0.0));
  tabControlPoint.push_back(Point(1.0, 1.0, 0.0));
  tabControlPoint.push_back(Point(1.5, 0.5, 0.0));

  tabControlPoint2.clear();
  tabControlPoint2.push_back(Point(-1.0, 1.0, 0.0));
  tabControlPoint2.push_back(Point(-0.5, 1.7, 0.0));
  tabControlPoint2.push_back(Point(1.0, 2.0, 0.0));
  tabControlPoint2.push_back(Point(1.5, 1.5, 0.0));

  //dessin d'un repere
  // drawPoint(Point(0, 0, 0));
  // glColor3f(0, 1.0, 0);
  // drawVector(Point(0, 0, 0), Vector(0, 0.5, 0));
  // glColor3f(0, 1.0, 0);
  // drawVector(Point(0, 0, 0), Vector(0.5, 0, 0));
  drawAxes();
  //fin du repere


  //========================================tp1
  /*
  glColor3f(1.0, 1.0, 1.0);

  Point p1 = Point(-1, -1.5, 0.0); //point a projeter
  Point p2 = Point(1, 1, 0); //point du vecteur
  Point p3 = Point(-1, -1, 0); //point du vecteur
  Vector v1 = Vector(-1, -1, 0); //vecteur sur lequel on projete le point p1

  drawPoint(p1);
  drawVector(p2, v1);
  Point pointOnLine = p1.projectOnLine(p2, p3); //on recupere le point projeté sur le vecteur
  drawSegment(p2, pointOnLine);
  glColor3f(0.5, 0.2, 0.0); //on change la couleur
  drawPoint(pointOnLine); //et on l'affiche
  */


  //========================================tp2
  // Point tabPointsOfCurve[4] = {Point(-.5, -1, 0), Point(-1, 1, 0), Point(1, 1, 0), Point(1, -1, 0)};
  // drawCurve(tabPointsOfCurve, 4);


  Point p0 = Point(0.0, 0.0, 0.0);
  Point p1 = Point(2.0, 0.0, 0.0);
  Vector v0 = Vector(1.0, 1.0, 0.0);
  Vector v1 = Vector(1.0, -1.0, 0.0);
  Point tab[nbU];
  Point tab2[nbU];

  //hermiteCubicCurve(p0, p1, v0, v1, nbU, tab);
  //drawCurve(tab, nbU);

  //========================================TP2 exercice2
  // glColor3f(0, 1, 0);
  // drawCurve(tabControlPoint);

  // glColor3f(1, 1, 1);
  // bezierCurveByBernstein(tabControlPoint, nbControlPoint, nbU, tab);
  // drawCurve(tab, nbU);

  //========================================TP2 exercice3
  // glColor3f(0, 0, 1);
  // bezierCurveByCasteljau(tabControlPoint, nbControlPoint, nbU, tab);
  // drawCurve(tab, nbU);
  

  //========================================TP surface parametrique
  // glColor3f(0, 1, 0);
  // bezierCurveByCasteljau(tabControlPoint, nbControlPoint, nbU, tab);
  // bezierCurveByCasteljau(tabControlPoint2, nbControlPoint, nbU, tab2);
  // drawCurve(tab, nbU);
  // drawCurve(tab2, nbU);
  // surfaceReglee(tab, tab2, nbU);

  //========================================TP representation surfacique
  //Exercice 1: facettisation d'un cylindre
  // glColor3f(0, 1, 0);
  // facettisationCylindre(10, 20, 10);
  // drawCylinder(northPoints, southPoints);


  //Exercice 2: facettisation d'un cone
  // glColor3f(0, 1, 0);
  // Point sommet = Point(0, 0, 20);
  // facettisationCone(15, 20, sommet, 10);
  // drawCone(sommet, southPoints);

  //Exercice 3: facettisation d'une sphere
  // glColor3f(0, 1, 0);
  // facettisationSphere(20, nbMeridien, nbParallele);

  //========================================TP Modele Volumique
  //Exercice 1
  glColor3f(0, 1, 0);
  Point center = Point(0, 0, 0);
  //drawCube(center, 8.0);
  Cube cube = Cube(center, 8);
  cout << "avant sub" << endl;
  Point v[(int)pow(4, 3)];
  cube.subdiviseCube(4, v);
  for(int i; i < (int)pow(4, 3); i++)
  {
    cout << v[i] << endl;
  }
  cout << "apres sub" << endl;
  drawCube(v, 8, (int)pow(4, 3));
  cout << "apres draw" << endl;
}



// ========================================================================================================= //
// ========================================================================================================= //
// ========================================================================================================= //
// =================================================== FUNCTION ============================================ //
// ========================================================================================================= //
// ========================================================================================================= //
// ========================================================================================================= //

void drawAxes()
{
  /* Length of axes */
  double len = 10.0;
  glColor3f(1.0,1.0,1.0);
  glBegin(GL_LINES);
  glVertex3d(0,0,0);
  glVertex3d(len,0,0);
  glVertex3d(0,0,0);
  glVertex3d(0,len,0);
  glVertex3d(0,0,0);
  glVertex3d(0,0,len);
  glEnd();
}

void drawPoint(const Point& p)
{
  glPointSize(1);
  glBegin(GL_POINTS);
    //glColor3f(0.0, 0.2, 0.5);
    glVertex3f(p.getX(), p.getY(), p.getZ());
  glEnd();
}

void drawVector(const Point& p, const Vector& v)
{
  glBegin(GL_LINES);
    glVertex3f(p.getX(), p.getY(), p.getZ());
    glVertex3f(p.getX()+v.getX(), p.getY()+v.getY(), p.getZ()+v.getZ());
  glEnd();
}

void drawSegment(const Point& p1, const Point& p2)
{
  glBegin(GL_LINES);
    glVertex3f(p1.getX(), p1.getY(), p1.getZ());
    glVertex3f(p2.getX(), p2.getY(), p2.getZ());
  glEnd();
}

void drawCurve(const vector<Point> tabPointsOfCurve)
{
  glBegin(GL_LINE_STRIP);
  for(int i = 0; i < tabPointsOfCurve.size(); i++)
  {
    glVertex3f(tabPointsOfCurve[i].getX(), tabPointsOfCurve[i].getY(), tabPointsOfCurve[i].getZ());
  }
  glEnd();
  for(int i = 0; i < tabPointsOfCurve.size(); i++)
    drawPoint(tabPointsOfCurve[i]);
}

void drawCurve(const Point* tabPointsOfCurve, const long nbPoints)
{
  glBegin(GL_LINE_STRIP);
  for(int i = 0; i < nbPoints; i++)
  {
    glVertex3f(tabPointsOfCurve[i].getX(), tabPointsOfCurve[i].getY(), tabPointsOfCurve[i].getZ());
  }
  glEnd();
  for(int i = 0; i < nbPoints; i++)
    drawPoint(tabPointsOfCurve[i]);
}

void movePoint(int x, int y)
{
  tabControlPoint[1].setX(pixelToOrthoX(x));
  tabControlPoint[1].setY(pixelToOrthoY(y));
  cout << " x = " << tabControlPoint[1].getX() << " y = " << tabControlPoint[1].getY() << endl;
  window_display();
}

double pixelToOrthoX(int x)
{
  return MINORTHO + (double) x / (double) WIDTH * MAXORTHO * 2;
}

double pixelToOrthoY(int y)
{
  return MAXORTHO - (double) y / (double) HEIGHT * MAXORTHO * 2;
}

void hermiteCubicCurve(Point p0, Point p1, Vector v0, Vector v1, long nbU, Point* tab)
{
  //calculer les coef
  Point a, b, c, d;
  d = Point(p0.getX(), p0.getY(), p0.getZ());
  c = Point(v0.getX(), v0.getY(), v0.getZ());
  b = Point(-3 * p0.getX() + 3 * p1.getX() - 2 * v0.getX() - v1.getX(),
   -3 * p0.getY() + 3 * p1.getY() - 2 * v0.getY() - v1.getY(), 0);
  a = Point(2 * p0.getX() - 2 * p1.getX() + v0.getX() + v1.getX(),
   2 * p0.getY() - 2 * p1.getY() + v0.getY() + v1.getY(), 0);

  for(int i = 0; i < nbU; i++)
  {
    double u = (double) i / (nbU - 1);
    tab[i] = Point((a.getX() * pow(u, 3) + b.getX() * pow(u, 2) + c.getX() * u + d.getX()),
      a.getY() * pow(u, 3) + b.getY() * pow(u, 2) + c.getY() * u + d.getY(), 0);
  }
}

void bezierCurveByBernstein(Point* tabControlPoint, long nbControlPoint, long nbU, Point* tab)
{
  int n = nbControlPoint - 1;
  Point b;
  for(int i = 0; i < nbU; i++)
  {
    double u = (double) i / (nbU - 1);
    b = Point(0.0, 0.0, 0.0);
    Point p;
    for(int j = 0; j <= n; j++)
    {
      b.setX(b.getX() + (((fact(n) / (fact(j) * fact(n - j))) * pow(u, j) * pow((1 - u), (n - j))) * 
        tabControlPoint[j].getX()));

      b.setY(b.getY() + (((fact(n) / (fact(j) * fact(n - j))) * pow(u, j) * pow((1 - u), (n - j))) * 
        tabControlPoint[j].getY()));
    }
    tab[i] = Point(b.getX(), b.getY(), 0);
  }
}

int fact(int n)
{
  if(n == 0 || n == 1)
    return 1;
  return n * fact(n-1);
}

void bezierCurveByCasteljau(vector<Point> tabControlPoint, long nbControlPoint, long nbU, Point* tab)
{
  int n = nbControlPoint - 1;
  for(int i = 0; i < nbU; i++)
  {
    double u = (double) i / (nbU -1);
    tab[i] = casteljauRec(n, 0, u, tabControlPoint);
  }
}

Point casteljauRec(int k, int i, double u, vector<Point> tabControlPoint)
{
  if(k == 0)
  {
    return tabControlPoint[i];
  }
  Point p1 = casteljauRec(k - 1, i, u, tabControlPoint);
  Point p2 = casteljauRec(k - 1, i + 1, u, tabControlPoint);
  //drawSegment(p1, p2);
  return (((double)(1 - u) * p1) + (u * p2));
}

void surfaceReglee(Point* tab, Point* tab2, long nbU)
{
  for(int i = 0; i < nbU; i++)
  {
    drawSegment(tab[i], tab2[i]);
    for(int j = 0; j < nbU + 1; j++)
    {
      Point p = segmentPoint(tab[i], tab2[i], j, nbU);
      //drawPoint(p);
      if(i < nbU - 1 && j > 0)
      {
        Point p0 = segmentPoint(tab[i], tab2[i], j - 1, nbU);
        Point p1 = segmentPoint(tab[i + 1], tab2[i + 1], j - 1, nbU);
        glBegin(GL_TRIANGLES);
          glVertex3f(p0.getX(), p0.getY(), p0.getZ());
          glVertex3f(p1.getX(), p1.getY(), p1.getZ());
          glVertex3f(p.getX(), p.getY(), p.getZ());
        glEnd();
      }
      if(i > 0 && j < nbU)
      {
        Point p0 = segmentPoint(tab[i - 1], tab2[i - 1], j + 1, nbU);
        Point p1 = segmentPoint(tab[i], tab2[i], j + 1, nbU);
        glBegin(GL_TRIANGLES);
          glVertex3f(p0.getX(), p0.getY(), p0.getZ());
          glVertex3f(p1.getX(), p1.getY(), p1.getZ());
          glVertex3f(p.getX(), p.getY(), p.getZ());
        glEnd();
      }
    }
  }
}

Point segmentPoint(Point p1, Point p2, int i, int nbU)
{
  Vector v1 = Vector(i*(p2.getX() - p1.getX())/nbU, i*(p2.getY() - p1.getY())/nbU, i*(p2.getZ() - p1.getZ())/nbU);
  return Point(p1.getX()+v1.getX(), p1.getY()+v1.getY(), p1.getZ()+v1.getZ());
}

void facettisationCylindre(double rayon, double hauteur, int nbMeridien)
{
  for (int i = 0; i < nbMeridien; i++)
  {
    double tetha = 2 * PI * ((double) i / nbMeridien);
    northPoints.push_back(Point(rayon * cos(tetha), rayon * sin(tetha), -hauteur/2));
    southPoints.push_back(Point(rayon * cos(tetha), rayon * sin(tetha), hauteur/2));
  }
}

void drawCylinder(vector<Point> northPoints, vector<Point> southPoints)
{
  glBegin(GL_POLYGON);
  glColor3f(0, 1, 0);
  for(int i = 0; i < northPoints.size(); i++)
  {
    glVertex3f(northPoints[i].getX(), northPoints[i].getY(), northPoints[i].getZ());
  }
  glEnd();
  
  glBegin(GL_POLYGON);
  glColor3f(0, 0, 1);
  for(int i = 0; i < southPoints.size(); i++)
  {
    glVertex3f(southPoints[i].getX(), southPoints[i].getY(), southPoints[i].getZ());
  }
  glEnd();

  for(int i = 0; i < northPoints.size(); i++)
  {
    glColor3f(1, 0, 0);
    drawSegment(northPoints[i], southPoints[i]);
  }
}

void facettisationCone(float rayon, float hauteur, Point sommet, int nbMeridien)
{
  for (int i = 0; i < nbMeridien; i++)
  {
    double tetha = 2 * PI * ((double) i / nbMeridien);
    northPoints.push_back(Point(rayon * cos(tetha), rayon * sin(tetha), -hauteur/2));
    southPoints.push_back(Point(rayon * cos(tetha), rayon * sin(tetha), hauteur/2));
  }
}

void drawCone(Point sommet, vector<Point> basePoints)
{
  glBegin(GL_TRIANGLE_FAN);
    glVertex3f(sommet.getX(), sommet.getY(), sommet.getZ());
    for(int i = 0; i < basePoints.size(); i++)
    {
      glVertex3f(basePoints[i].getX(), basePoints[i].getY(), basePoints[i].getZ());
    }
    glVertex3f(basePoints[0].getX(), basePoints[0].getY(), basePoints[0].getZ());
  glEnd();
}

void facettisationSphere(float rayon, int nbMeridien, int nbParallele)
{
  for(int i = 0; i < nbMeridien; i++)
  {
    double tetha = 2 * PI * ((double) i / nbMeridien);
    glBegin(GL_LINE_STRIP);
    for(int j = 0; j <= nbParallele; j++)
    {
      double phy = PI * ((double) j / nbParallele);
      Point tmp = Point(rayon * sin(phy) * cos(tetha), rayon * sin(phy) * sin(tetha), rayon * cos(phy));
      glVertex3f(tmp.getX(), tmp.getY(), tmp.getZ());
    }
    glEnd();
  }

  for(int i = 0; i <= nbParallele; i++)
  {
    double phy = PI * ((double) i / nbParallele);
    glBegin(GL_LINE_STRIP);
    for(int j = 0; j <= nbMeridien; j++)
    {
      double tetha = 2 * PI * ((double) j / nbMeridien);
      Point tmp = Point(rayon * sin(phy) * cos(tetha), rayon * sin(phy) * sin(tetha), rayon * cos(phy));
      glVertex3f(tmp.getX(), tmp.getY(), tmp.getZ());
    }
    glEnd();
  }
}

void drawCube(Point center, float size)
{
  Cube cube(center, size);

  glBegin(GL_POLYGON);
    glVertex3f(cube.p1.getX(), cube.p1.getY(), cube.p1.getZ());
    glVertex3f(cube.p2.getX(), cube.p2.getY(), cube.p2.getZ());
    glVertex3f(cube.p3.getX(), cube.p3.getY(), cube.p3.getZ());
    glVertex3f(cube.p4.getX(), cube.p4.getY(), cube.p4.getZ());
  glEnd();

  glBegin(GL_POLYGON);
    glVertex3f(cube.p5.getX(), cube.p5.getY(), cube.p5.getZ());
    glVertex3f(cube.p6.getX(), cube.p6.getY(), cube.p6.getZ());
    glVertex3f(cube.p7.getX(), cube.p7.getY(), cube.p7.getZ());
    glVertex3f(cube.p8.getX(), cube.p8.getY(), cube.p8.getZ());
  glEnd();

  glBegin(GL_POLYGON);
    glVertex3f(cube.p1.getX(), cube.p1.getY(), cube.p1.getZ());
    glVertex3f(cube.p5.getX(), cube.p5.getY(), cube.p5.getZ());
    glVertex3f(cube.p6.getX(), cube.p6.getY(), cube.p6.getZ());
    glVertex3f(cube.p2.getX(), cube.p2.getY(), cube.p2.getZ());
  glEnd();

  glBegin(GL_POLYGON);
    glVertex3f(cube.p2.getX(), cube.p2.getY(), cube.p2.getZ());
    glVertex3f(cube.p6.getX(), cube.p6.getY(), cube.p6.getZ());
    glVertex3f(cube.p7.getX(), cube.p7.getY(), cube.p7.getZ());
    glVertex3f(cube.p3.getX(), cube.p3.getY(), cube.p3.getZ());
  glEnd();

  glBegin(GL_POLYGON);
    glVertex3f(cube.p7.getX(), cube.p7.getY(), cube.p7.getZ());
    glVertex3f(cube.p3.getX(), cube.p3.getY(), cube.p3.getZ());
    glVertex3f(cube.p4.getX(), cube.p4.getY(), cube.p4.getZ());
    glVertex3f(cube.p8.getX(), cube.p8.getY(), cube.p8.getZ());
  glEnd();

  glBegin(GL_POLYGON);
    glVertex3f(cube.p1.getX(), cube.p1.getY(), cube.p1.getZ());
    glVertex3f(cube.p5.getX(), cube.p5.getY(), cube.p5.getZ());
    glVertex3f(cube.p8.getX(), cube.p8.getY(), cube.p8.getZ());
    glVertex3f(cube.p4.getX(), cube.p4.getY(), cube.p4.getZ());
  glEnd();
}

void drawCube(Point* v, float size, int len)
{
  if(len == 1)
  {
    drawCube(v[0], size);
  }
  for (int i = 0; i < len; ++i)
  {
    float sizeSub = v[1].getZ() - v[0].getZ();
    drawCube(v[i], sizeSub);
  }
}