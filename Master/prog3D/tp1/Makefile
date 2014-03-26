CC=g++
CFLAGS=-lX11 -lglut -lGL
EXEC=scene

all: $(EXEC)

scene: vector.o point.o cube.o TP_OPENGL.o
	$(CC) -o scene vector.o point.o cube.o TP_OPENGL.o $(CFLAGS)

vector.o: Vector.cpp
	$(CC) -o vector.o -c Vector.cpp $(CFLAGS)

point.o: Point.cpp
	$(CC) -o point.o -c Point.cpp $(CFLAGS)

cube.o: Cube.cpp
	$(CC) -o cube.o -c Cube.cpp $(CFLAGS)

TP_OPENGL.o: TP_OPENGL.cpp Vector.h Point.h Cube.h
	$(CC) -o TP_OPENGL.o -c TP_OPENGL.cpp $(CFLAGS)

clean:
	rm -rf *.o

mrproper: clean
	rm -rf $(EXEC)