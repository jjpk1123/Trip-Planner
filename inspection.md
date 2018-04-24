# Inspection - *t17*

Inspection | Details 
--- | :--- 
Subject | Review the entirety of Optimize.java 
Meeting | Tuesday, 24th of April, in Class
Checklist | _To be filled in later_

## Roles
Name | Role | Preparation Time
--- | ---: | ---: 
Julien|Moderator|30 minutes
Mike|Maintainer| 45 minutes
Ezra|Tester|30 minutes

## Log
file:line | defect | high/med/low | who found it | github issue#
--- | :--- | :--- | :--- | :---
Optimize.java:all|Should we have placesArray and distanceTable be global variables? Every method need them|med|Julien|
Optimize.java:37|Should not need two arrays|low|Julien|
Optimize.java:59,62,75|Needs test coverage|low|Julien|
Optimize.java:56,65,71|Optimal way for using these flags?|low|Julien|
Optimize.java:152,153|Far too complex of a line, should have a method for it|med|Julien|
Optimize.java:162,278|Repeated code for constructing array to return, should have another method|med|Julien|
Optimize.java:181|Too complex, create method for threeOptCurrentDistance|low|Julien|
Optimize.java:187-270|Too cognitively complex, impossible to ensure testing lines we think we are, write new methods|high|Julien|
Optimize.java:308|Too many variables|low|Julien
Optimize.java:313,318,323 | Manual array copy should be System.arraycopy | low | Julien | 
Optimize.java:335|Too many variables|low|Julien|
Optimize.java:47|Do we need to make resultArray equal to places here?|low|Ezra|
Optimize.java:146|'Delta' variable name could be more descriptive|low|Ezra|
Optimize.java:270|'continue' statement does nothing as it is last in loop|low|Ezra|
Optimize.java:335|Global variables would make this method take less params|medium|Ezra|
Optimize.java:358|Is there a built in indexOf that could do the same?|low|Ezra|
Optimize.java:25,28,31|(Incorrect & Hardcoded) values used in conditionals.|low|Mike|
Optimize.java:187-270|Reverse cases, start from 7, work down to 1.|low|Mike|
Optimize.java:312,317,322|Manual array copies -> System.arraycopy().|med|Mike|
Optimize.java:9|Make global variable of places.size() and use it instead of computing it in every for loop. (places.size() / distanceArray.length is cited > 30 times, and is re-computed n*30 times in the file).|med|Mike|
