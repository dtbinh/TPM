VERSION - 2.4.8 eclipse user

----------INSTALLATION-----------------

Step 1: Importing the project under Eclipse:
----------------------------------
If you have not yet unziped this archive :
File > Import > Existing project into workspace > select archive file > browse to Tk2_Eclipse.zip

If you have already unziped this archive :
File > Import > Existing project into workspace > select root directory > browse to the unzip Tk2_Eclipse directory

Step 2: Launch configuration under Eclipse:
-----------------------------------
Run -> Run configurations -> java application -> create new launch configuration -> give a title
Project: Tk2_project
Main Class: madkit.boot.Madkit

>Arguments
Program Arguments: madkit.kernel.Booter --graphics --config turtlekit2.cfg

Optional Step: Simulation Auto-Launch:
-----------------------
Add the path of your simulation.xml in Simulations.cfg

----------TROUBLESHOUTING-----------------

if Java3D crash:
Project Properties > Java Build Path > Libraries > J3DCore.jar > Native Libraries Location > 
Edit > Workspace > Project/lib 


LICENCE:
--------

/*
 * TurtleKit - An Artificial Life Simulation Platform
 * Copyright (C) 2000-2010 Fabien Michel, Gregory Beurier
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
 
 
 JDK 1.6
Embedded Libraries 	- j3d (core, utils, vecmath) 
					- madkit (kernel, utils, boot) 
					- jfreechart (jcommons)
					- xml (xerces, xml-api) 
					- jython 
					- infonode (idw-gpl)


 