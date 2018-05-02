# Inspection - *t17*

Inspection | Details 
--- | :--- 
Subject | Review the entirety of Optimize.java 
Meeting | Tuesday, 24th of April, in Class
Checklist | Fox

## Roles
Name | Role | Preparation Time
--- | ---: | ---: 
Julien|Moderator|30 minutes
Mike|Maintainer| 45 minutes
Ezra|Tester|30 minutes

## Log
file:line | defect | high/med/low | who found it | github issue#
--- | :--- | :--- | :--- | :---
Optimize.java:all|Should we have placesArray and distanceTable be global variables? Every method need them|med|Julien|#309
Optimize.java:152,153|Far too complex of a line, should have a method for it|med|Julien|#310
Optimize.java:162,278|Repeated code for constructing array to return, should have another method|med|Julien|#311
Optimize.java:181|Too complex, create method for threeOptCurrentDistance|low|Julien|#312
Optimize.java:187-270|Too cognitively complex, impossible to ensure testing lines we think we are, write new methods|high|Julien|#312
Optimize.java:313,318,323 | Manual array copy should be System.arraycopy | low | Julien | #313
Optimize.java:25,28,31|(Incorrect & Hardcoded) values used in conditionals.|low|Mike|#314
Optimize.java:9|Make global variable of places.size() and use it instead of computing it in every for loop. (places.size() / distanceArray.length is cited > 30 times, and is re-computed n30 times in the file).|med|Mike|#315
