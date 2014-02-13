#ifndef POINT_H
#define POINT_H

#include "Vector.h"

class Point
{
	private:
		double _x, _y, _z;
	public:
		Point();
		Point(double x, double y, double z);
		Point(const Point& p);
		~Point();

		double getX() const;
		double getY() const;
		double getZ() const;

		void setX(double);
		void setY(double);
		void setZ(double);

		Point projectOnLine(Point point1Line, Point point2Line);
		Point projectOnLine(Point p, Vector vecteur);
		Point projectOnPlan(Point pointOnPlane, Vector normalOfPlane);
};

Point operator*(const double &a, const Point &p);
Point operator+(const Point &p1, const Point &p2);
// void operator=(Point &p1, const Point &p2);

#endif