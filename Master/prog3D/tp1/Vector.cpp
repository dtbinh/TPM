#include <iostream>
#include <cmath>
#include "Vector.h"
#include "Point.h"

using namespace std;

#define PI 3.14159265359

Vector::Vector()
{
	_x = 0;
	_y = 0;
	_z = 0;
}

Vector::Vector(double x, double y, double z)
{
	_x = x;
	_y = y;
	_z = z;
}

Vector::Vector(const Vector& p)
{
	_x = p._x;
	_y = p._y;
	_z = p._z;
}

/*
Vector::Vector(Point p1, Point p2)
{
	_x = p2.getX() - p1.getX();
	_y = p2.getY() - p1.getY();
	_z = p2.getZ() - p1.getZ();
}
*/

Vector::~Vector(){}

double Vector::getX() const {return _x;}
double Vector::getY() const {return _y;}
double Vector::getZ() const {return _z;}


void Vector::setX(double x){_x = x;}
void Vector::setY(double y){_y = y;}
void Vector::setZ(double z){_z = z;}

double Vector::norme()
{
	return sqrt(pow(_x, 2)+pow(_y, 2)+pow(_z, 2));
}

void Vector::normalize()
{
	double n = norme();
	_x = _x / n;
	_y = _y / n;
	_z = _z / n;
}

double Vector::scalar(Vector vector2)
{
	return _x*vector2._x + _y*vector2._y + _z*vector2._z;
}

Vector Vector::vectoriel(Vector vector2)
{
	return Vector((_y*vector2._z - _z*vector2._y), (_z*vector2._x - _x*vector2._z), (_x*vector2._y - _y*vector2._x));
}

double Vector::angle(Vector vector2)
{
	double cosinus = scalar(vector2)/(vector2.norme() * norme());
	double sinus = vectoriel(vector2).norme()/(vector2.norme() * norme());

	if(sinus < 0)
		return acos((-PI/2) * cosinus);
	return acos(cosinus);
}