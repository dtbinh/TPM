Aide mémoire
============

Bouton Setup:
-------------

to setup
	;; initialisation, création d'un ensemble d'agents
	create-abeilles nbAbeilles [
		set color green
		setxy random-xcor random-ycor
		set size 5
		...
	]
end

Attributs / Varibles locales / Globales:
----------------------------------------

 Attributs:
 - - - - - 
;; avant setup et init lors de la création
abeilles-own [ ma-reine ]

variables locales:
- - - - - - - - - 
let var-name valeur

variables globales:
- - - - - - - - - -

globals [ max-energy ]

Déplacement d'un agent:
-----------------------

fd valeur ;; avancer
rt valeur ;; tourner a droite de valeur degre
lt valeur ;; tourner a gauche de valeur degre

AgentSet: sous ensemble d'agent
-------------------------------

turtles with [ color = red ]
turtles in-radius 10 ;; tous les turtles qui se trouvent a 10 unités
