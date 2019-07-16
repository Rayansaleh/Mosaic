This program is design to divide a map into a number of pieces, like a mosaic.

The following indication are given in the map:

The number of row.
The number of colon.
The number of color needed per pieces
The max size of piece.


To solve that problem, each zone of the map will be divided in half either vertically or horizontally.

At the beginning there is only one zone (the all map). It will be cut in half, from now on there is two  zones.

Those two zone will be cut in half, there will then be four zone. And it goes on until the all map is cut into valid pieces.



To choose if the zone will be cut vertically or horizontally we use math, more specficaly probability.

There is elements that define a piece, mostly the number of color inside and the size of it.

So the more conditions for a piece are respected the higher the probability is, so the most likely the cut will be done for this precise zone.

A recursive is used to try out every possible cut before choosing one. So the cut are made their probability of bringing out valid pieces is calculed and the one with the higher probability is kept.



The program print the number of pieces, and there coordinates.



Here is the way to execute it :
'''
java -jar out/artifacts/Mosaic_jar/Mosaic.jar 'File containing the map'
'''
