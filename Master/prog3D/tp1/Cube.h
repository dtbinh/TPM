#ifndef CUBE_H
#define CUBE_H

#include "Point.h"

class Cube
{
	public:
		//face avant haut droit bas gauche
		Point p1, p2, p3, p4;
		//face arriere
		Point p5, p6, p7, p8;
		Cube();
		Cube(Point center, float size);
		Cube(const Cube& c);
};

#endif