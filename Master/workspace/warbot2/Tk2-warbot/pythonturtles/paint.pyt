from java.lang import Math
from turtlekit.kernel import Turtle
from java.awt import Color

class Paint(Turtle):
	cpt=0

	def create(self):
		self.turnRight(self.cpt)
		self.cpt = self.cpt + 1
		self.fd(1)
		self.setPatchColor(Color.white)

def setup():
	clearAll()
	addTurtle(Paint())

makeProcedure("setup")	

def create():
	askTurtles("create()")

def jump():
	askTurtles("fd(20)")

makeProcedure("create")
makeProcedure("jump")

def doIt():
	for x in range(0,360):
		askTurtles("setPatchColor(Color.white) turnRight("+x.toString()+") fd(1)")

makeProcedure("doIt")