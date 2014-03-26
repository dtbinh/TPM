#include <iostream>
#include <vector>
#include <math.h>
#include "Point.h"
#include "Cube.h"

using namespace std;

Cube::Cube(){}

Cube::Cube(Point center, float _size)
{
	float rayon = _size / 2;
	size = _size;
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

void Cube::subdiviseCube(int nb, Point* v)
{
	float sizeSousCube = size / nb;
	int l = 0;
	for(int i = 0; i < nb; i++)
	{
		for (int j = 0; j < nb; ++j)
		{
			for (int k = 0; k < nb; ++k)
			{
				Point center = Point((p1.getX() + sizeSousCube * i) + (sizeSousCube / 2),
					(p1.getY() - sizeSousCube * j) - (sizeSousCube / 2),
					(p1.getZ() + sizeSousCube * k) + (sizeSousCube / 2));
				v[l] = center;
				l++;
			}
		}
	}
}
