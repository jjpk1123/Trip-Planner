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
Mike|Maintainer| _Mike, update when you are finished with pre-inspection_
Ezra|Tester| _Ezra, update when you are finished with pre-inspection_

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
