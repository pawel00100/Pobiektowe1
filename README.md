# Pobiektowe1
World simulation

This desktop app simulates world with animals on it. Animals can eat grass to increase their energy level, move, reproduce giving part of its simple genome to the offspring.

Short video:
https://youtu.be/22znvoxVjhc
Colored bars represent genses inside a genome.


TODO:

-Blocking main threads by Swing threads (this is why it is locked to 1000 runs / second - without UI it can easily run large map over 10000/sec)

-Boundary could be a separate class from RectangleMap

-Exceptions could be handled better

-UI with larger map sizes takes long time to open


May need external JSObject library from https://github.com/stleary/JSON-java/releases
