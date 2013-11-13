#!/bin/bash
#do more write less :)
repCourant=`pwd`
libPath="$repCourant"/lib
binPath="$repCourant"/bin

java -Djava.library.path=$libPath -Dfile.encoding=Cp1252 -cp "lib/*:.":$binPath madkit.boot.Madkit madkit.kernel.Booter --graphics --config turtlekit2.cfg
