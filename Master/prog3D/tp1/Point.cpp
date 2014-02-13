#include <iostream>
#include "Point.h"
#include "Vector.h"

using namespace std;

Point::Point()
{
	_x = 0;
	_y = 0;
	_z = 0;
}

Point::Point(double x, double y, double z)
{
	_x = x;
	_y = y;
	_z = z;
}

Point::Point(const Point& p)
{
	_x = p._x;
	_y = p._y;
	_z = p._z;
}

Point::~Point(){}

double Point::getX() const {return _x;}
double Point::getY() const {return _y;}
double Point::getZ() const {return _z;}


void Point::setX(double x){_x = x;}
void Point::setY(double y){_y = y;}
void Point::setZ(double z){_z = z;}

Point Point::projectOnLine(Point point1Line, Point point2Line)
{
	Point point = Point();
	Vector u = Vector(point2Line._x - point1Line._x, point2Line._y - point1Line._y, point2Line._z - point1Line._z);
	//Vector ba = Vector(point2Line._x - _x, point2Line._y - _y, point2Line._z - _z);
	Vector ba = Vector(_x - point1Line._x, _y - point1Line._y, _z - point1Line._z);
	double normeBa = ba.scalar(u)/u.norme();
	u.normalize();
	point._x = point1Line.getX() + u.getX() * normeBa;
	point._y = point1Line.getY() + u.getY() * normeBa;
	point._z = point1Line.getZ() + u.getZ() * normeBa;

	return point;
}

Point Point::projectOnLine(Point p, Vector vecteur)
{
	Point point = Point();
	Vector ba = Vector(p._x - _x, p._y - _y, p._z - _z);
	double normeBa = ba.scalar(vecteur)/vecteur.norme();
	vecteur.normalize();
	point._x = p._x + vecteur.getX() * normeBa;
	point._y = p._y + vecteur.getY() * normeBa;
	point._z = p._z + vecteur.getZ() * normeBa;

	return point;
}

Point Point::projectOnPlan(Point pointOnPlane, Vector normalOfPlane)
{
	Point point = Point();
	Vector ma = Vector(pointOnPlane._x - _x, pointOnPlane._y - _y, pointOnPlane._z - _z);
	double normeMM = ma.scalar(normalOfPlane)/normalOfPlane.norme();

	point._x = _x + normalOfPlane.getX() * normeMM;
	point._y = _y + normalOfPlane.getY() * normeMM;
	point._z = _z + normalOfPlane.getZ() * normeMM;

	return point;
}

Point operator*(const double &a, const Point &p)
{
	return Point(p.getX() * a, p.getY() * a, p.getZ() * a);
}

Point operator+(const Point &p1, const Point &p2)
{
	return Point(p1.getX() + p2.getX(), p1.getY() + p2.getY(), p1.getZ() + p2.getZ());
}

// void operator=(Point &p1, const Point &p2)
// {
// 	p1.setX(p2.getX());
// 	p1.setY(p2.getY());
// 	p1.setZ(p2.getZ());
// }