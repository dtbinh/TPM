#ifndef VECTOR_H
#define VECTOR_H

class Vector
{
	private:
		double _x, _y, _z;
	public:
		Vector();
		Vector(double x, double y, double z);
		Vector(const Vector& p);
		~Vector();

		double getX() const;
		double getY() const;
		double getZ() const;

		void setX(double x);
		void setY(double y);
		void setZ(double z);

		double norme();
		void normalize();
		double scalar(Vector vector2);
		Vector vectoriel(Vector vector2);
		double angle(Vector vector2);
};

#endif