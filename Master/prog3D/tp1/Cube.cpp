#include <iostream>
#include "Point.h"
#include "Cube.h"

using namespace std;

Cube::Cube(){}

Cube::Cube(Point center, float size)
{
	float rayon = size / 2;
	p1 = Point(center.getX() - rayon, center.getY() + rayon, center.getZ() - rayon);
	p2 = Point(center.getX() + rayon, center.getY() + rayon, center.getZ() - rayon);
	p3 = Point(center.getX() + rayon, center.getY() - rayon, center.getZ() - rayon);
	p4 = Point(center.getX() - rayon, center.getY() - rayon, center.getZ() - rayon);

	p5 = Point(center.getX() - rayon, center.getY() + rayon, center.getZ() + rayon);
	p6 = Point(center.getX() + rayon, center.getY() + rayon, center.getZ() + rayon);
	p7 = Point(center.getX() + rayon, center.getY() - rayon, center.getZ() + rayon);
	p8 = Point(center.getX() - rayon, center.getY() - rayon, center.getZ() + rayon);
}

Cube::Cube(const Cube& c)
{
	p1 = c.p1;
	p2 = c.p2;
	p3 = c.p3;
	p4 = c.p4;
	p5 = c.p5;
	p6 = c.p6;
	p7 = c.p7;
	p8 = c.p8;
}
