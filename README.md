# Teleport

## DESIGN:
The problem is solved by keeping a list of ports along with its adjacent ports. List<Port P, List<Ports adjacent to P>>. 
I have choosen LinkedHashMap to be able to maintain unique pair of (key,value) where in key is the port and value is list of ports teleportable from port P. Using LinkedHashMap we will be able to retain the order of ports mapped and HashMap helps with the faster retrival while traversing recursively. 
Static API's are implemented to solve the queries provided in input file.

## ASSUMPTIONS:
1. Assuming input will be similar to the sample input provided.
2. Assuming user input will always have the ports listed first, at the beginning of the input file.
3. Port names are lower and upper case alphabets (with or without space) and are in the format given in problem. Example: Washington - Baltimore. Additional ports can be added.
4. Queries text remains same execpt the variable parameters like ports and jumps which can be changed in input text file. Questions can be asked in any order.

## CORE CLASSES:
Port.java: 				Generic class representation of Port. 
TeleportsAdjMap.java:   Creates the HashMap of Ports. 
TeleportAPI.java:       Contains the static API's for the queries specified in the problem.
TeleportAPITest.java:   jUnit for the API's  in TeleportAPI.

## EXECUTION:
Invocation Order: TeleportMain.java -> TeleportParser.java(Creates TeleportsAdjMap.java) -> TeleportAPI.java

Appropriate exceptions are thrown when erronous input is provided. Certain input validations are not needed, so not providing them.
Also, for the simplicity of execution, have hardcoded the inputtext file name instead of accepting it as parameter.

Please import the project as "Existing Maven Projects" and run it in the IDE. Also providing alternate input file "teleportInput2.txt".
Can be also executed through command line if maven or java is available in system path.

## PROBLEM:
Problem 1: Teleportation System
You have discovered the secrets of teleportation and have several teleportation routes up and
running. Each route
allows instantaneous travel from one city to another. All routes are two way: if you can teleport
from city A
to city B, you can also teleport from city B to city A. You want to create a system to make it easier
for you to
answer specific questions about your network. You should assume anyone using your network
wants to travel only by teleportation.
Questions you must be able to answer:
1. What cities can I reach from city X with a maximum of N jumps?
2. Can someone get from city X to city Y?
3. Starting in city X, is it possible to travel in a loop (leave the city on one route and return on
another, without traveling along the same route twice)?
Input to the program will be a list of teleportation routes, followed by a list of queries.
### Example input:
Washington - Baltimore
Washington - Atlanta
Baltimore - Philadelphia
Philadelphia - New York
Los Angeles - San Fransisco
San Fransisco - Oakland
Los Angeles - Oakland
Seattle - New York
Seattle - Baltimore
cities from Seattle in 1 jumps
cities from Seattle in 2 jumps
can I teleport from New York to Atlanta
can I teleport from Oakland to Atlanta
loop possible from Oakland
loop possible from Washington
### Example output:
cities from Seattle in 1 jumps: New York, Baltimore
cities from Seattle in 2 jumps: New York, Baltimore, Philadelphia, Washington
can I teleport from New York to Atlanta: yes
can I teleport from Oakland to Atlanta: no
loop possible from Oakland: yes

loop possible from Washington: no