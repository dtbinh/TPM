from java.lang import Math
from turtlekit.kernel import Turtle
from java.awt import Color

class PythonTermite(Turtle):
	whatToDoNext=0

	def initWorld(self):
		self.setColor(Color.red)
		for x in range(0,120):
			for y in range(0,120):
				if Math.random() > 0.8:
					self.setXY(x,y)
					self.setPatchColor(Color.yellow)

	def init(self):
		self.setColor(Color.red)
		self.randomHeading()
		self.setX(Math.random()*120)
		self.setY(Math.random()*120)

	def wiggle(self):
		self.turnRight(Math.random()*45)
		self.turnLeft(Math.random()*45)
		self.fd(1)

	def getAway(self):
		if self.getPatchColor() == Color.black:
			self.whatToDoNext=1
		else:
			self.randomHeading()
			self.fd(20)
			self.whatToDoNext=0
			
	def searchForChip(self):
		self.wiggle()
		if self.getPatchColor() == Color.yellow:
			self.setPatchColor(Color.black)
			self.fd(20)
			self.whatToDoNext=2
		else:
			self.whatToDoNext=1

	def findNewPile(self):
		if self.getPatchColor() == Color.yellow:
			self.whatToDoNext=3
		else:
			self.wiggle()
			self.whatToDoNext=2

	def findEmptyPatch(self):
		self.wiggle();
		if self.getPatchColor() == Color.black:
			self.setPatchColor(Color.yellow)
			self.whatToDoNext=0
		else:
			self.whatToDoNext=3

	def makeIt(self):
		if self.whatToDoNext == 0:
			self.getAway()
		elif self.whatToDoNext == 1:
			self.searchForChip()		
		elif self.whatToDoNext == 2:
			self.findNewPile()
		else:
			self.findEmptyPatch()

#def setup():
#	clearAll()
#	crt(1)
#	for x in range(0,120):
#		for y in range(0,120):
#			if Math.random() > 0.6:
#				askTurtles('setXY(' + x.toString() + ","+ y.toString()+") setPatchColor(Color.yellow)")
#	clearT()		
#	for i in range(0,125):
#		addTurtle(PythonTermite())
#	askTurtles("init()")
	
#makeProcedure("setup")	

def setupWorld():
	clearAll()
	addTurtle(PythonTermite()) 
	askTurtles("initWorld()") #faster than asking an anonymous turtle to do it with askTurtles like in setup
	clearT()
	for i in range(0,125):
		addTurtle(PythonTermite())
	askTurtles("init()")
	
makeProcedure("setupWorld")	

def doIt():
	askTurtles("makeIt()")

makeProcedure("doIt")
