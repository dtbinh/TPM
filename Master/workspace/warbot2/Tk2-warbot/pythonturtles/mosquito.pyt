from java.lang import Math
from turtlekit.kernel import Turtle
from java.awt import Color

class PythonMosquito(Turtle):
	sunX=0
	sunY=0
	cpt=50
	whatToDoNext=0

	def init(self):
		self.home()
		self.setPatchColor(Color.yellow)
		self.sunX = self.xcor()
		self.sunY = self.ycor()

	def move(self):
		if Math.random() > 0.5:
			self.turnRight(15)
		
		else:
			self.turnLeft(15);

		self.fd(1)
		self.cpt = self.cpt - 1
		if self.cpt < 0:
			self.setHeading(self.towards(self.sunX,self.sunY))
			self.whatToDoNext=1

	def fall(self):
		self.fd(1)
		if self.distance(self.sunX,self.sunY) < 1:
			self.cpt = Math.random()*100
			self.whatToDoNext=0

	def fly(self):
		if self.whatToDoNext == 0:
			self.move()
		else:
			self.fall()		

def setup():
	clearAll()
	for i in range(0,110):
		addTurtle(PythonMosquito())
	askTurtles("init()")
	
makeProcedure("setup")	

def letsgo():
	askTurtles("fly()")

makeProcedure("letsgo")
