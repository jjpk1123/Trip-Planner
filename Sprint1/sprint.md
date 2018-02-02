# Sprint 1 - t17 - Quixotic Quetzals

## Goal

## A team effort to build a simple web application!

## Definition of Done

* Ready for demo / customer release.
* Sprint Review and Restrospectives completed.
* Product Increment release `v1.0` created on GitHub with appropriate version number and name, a description based on the template, and a zip of the repo.
* ~~Version in pom.xml should be `<version>1.0.0</version>`.~~
* ~~Unit tests for all new features and public methods at a minimum.~~
* ~~Coverage at least 50% overall and for each class.~~
* ~~Clean continuous integration build/test on master branch.~~

## Policies

* ~~Tests and Javadoc are written before/with code. ~~
* ~~All pull requests include tests for the added or modified code.~~
* Master is never broken.  If broken, it is fixed immediately.
* Always check for new changes in master to resolve merge conflicts locally before committing them.
* All changes are built and tested before they are committed.
* ~~Continuous integration always builds and tests successfully.~~
* All commits with more than 1 line of change include a task/issue number.
* ~~All Java dependencies in pom.xml.~~


## Metrics 

## Plan

Epics planned for this release.

* #1 README.md
* #2 team/eName.md
* #3 Resume
* #4 Distance Calculator

## Daily Scrums

## Review

#### Completed user stories (epics) in Sprint Backlog 
* *GDC math implementation*:  *The entire formula for finding the distance between two points was implemented. We tested this math and seem to have it working perfectly.*
* *Basic inputs and outputs*:  *The site was initially built with very basic functionality. Two boxes for input, a unit selector, and a output box. We needed this to test our parsing to make sure we were reading user inputs correctly.*
* *Parsing inputs and outputs*:  *We parsed the user input to split it, and convert it to a form we could use in our GDC math method. All formats were reduced down to a single common format so GDC could be simpler.*
* *Unit selection*:  *Unit selection changed which cosntant was used in GDC. This let our output be in either Kilometers or Miles.*
* *Stylize calculator*:  *Once functionality of the parser, the math, and the output was all sorted, we then made the website look better and more intuitive for the user. We added a calculate button instead of only calculating when a unit was selected. This is to make it easier for the user to do multiple different inputs without refreshing the page or having to switch units back and forth.*
* *Add mobile responsiveness*:  *After it looked nice, we had to make it look nice on mobile as well. When the screen size shrunk, we made all the feilds stack on each other instead of being side by side. This made it easier to read and use for portrait devices.*

#### Incomplete user stories / epics in Sprint Backlog 
* *Optional file load button*: *We did not have time to implement this button and have it work correctly. The button would be easy enough, but we did not have time for the file reader.*
* *User friendly error handling for incorrect formats *: *Although our calculator does not provide numerical output for incorrect formats of input, our error statement is "NaN". A user might not understand this, so we should have implemented a clearer form of communication to the user for this error.*


#### What went well
* *Communication*: *Slack was a great tool for us to all be able to speak with each other about issues we were having, or just to get feedback about how the website should look.*
* *Work ethic*: *We all seemed to want to have this done well and look nice, and we didn't wait until the last second to do it. I think we spread out our work well over the entire week.*

#### Problems encountered and resolutions
* *Bugs*: *Some of our code didn't work all that well at the start. We need to develop a better way to do unit testing, so only working code gets brought into master. All of the code we entered into master worked, but sometimes later down the road we would find a bug that was hard to see the source of. More in depth testing will be something we need to continute to work on next sprint. We got better towards the end of sprint 1, but we still need to spend some time on that.*
*

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we will change this time | have more face to face meetings | Keep updated who is working on what | always be responsive on slack
What we did well | Communication | Working on the most urgent tasks first | Slack was very helpful for our team
What we need to work on | Making sure each person tests their code | Make sure those tasks are well tested | Knowing who needs to work on what, and if something is already worked on
What we will change next time | Have everyone be accessible to speak about their code  | More testing on code blocks before they are thrown into master | Use zenhub to prioritize and show what work is being done
