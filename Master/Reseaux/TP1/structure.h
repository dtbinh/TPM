#ifndef STRUCT_H
#define STRUCT_H

struct req
{
	long etiq;
	int type;
	int op1;
	int op2;
};


struct rep
{
	long etiq;
	int res;
};

#endif


