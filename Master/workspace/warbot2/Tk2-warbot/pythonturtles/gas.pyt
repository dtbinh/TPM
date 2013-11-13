from java.lang import Math
from turtlekit.kernel import Turtle
from java.awt import Color

class Gas(Turtle):

	def initGas(self):
		self.setColor(Color.cyan)
		self.setXY((Math.random()*18)+1,Math.random()*150)

	def gasMove(self):
		if (self.xcor()+self.dx()) < 0 or (self.xcor()+self.dx()) >= self.getWorldWidth() or (self.ycor()+self.dy()) < 0 or (self.ycor()+self.dy()) >= self.getWorldHeight():
			self.randomHeading()
		for i in range(0,4):
			if self.getPatchColorAt(self.dx(),self.dy()) != Color.white and self.countTurtlesAt(self.dx(),self.dy()) == 0 :
				self.fd(1)
				break
			else:
				self.randomHeading()

def gasSetup():
	clearAll()
	crt(1)
	for x in range(0,150):
		askTurtles('setXY(20,' + x.toString() + ") setPatchColor(Color.white)")
	for x in range(0,150):
		askTurtles('setXY(0,' + x.toString() + ") setPatchColor(Color.white)")
	askTurtles("setHeading(90) setXY(20,75)")
	for x in range(0,2):
		askTurtles("setPatchColor(Color.black) fd(1)")
	clearT()
	for i in range (0,200):
		addTurtle(Gas())
	askTurtles("initGas()")
	
makeProcedure("gasSetup")	

def gasLive():
	askTurtles("gasMove()")

makeProcedure("gasLive")
