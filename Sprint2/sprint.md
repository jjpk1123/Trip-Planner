# Sprint 2 - t17 - Quixotic Quetzals

## Goal

### A mobile, responsive map and itinerary!
### Sprint Leader: Julien Pecquet

## Definition of Done

* Web application ready for demo and potential customer release.
* Sprint Review and Restrospectives completed (sprint.md).
* Product Increment release `v2.0` created on GitHub with appropriate version number and name, a description based on the Release Notes template, and the arhived files.
* Version in pom.xml should be `<version>2.0.0</version>`.
* Javadoc and unit tests for public methods.

## Policies

* Code adheres to Google style guides for Java and JavaScript.
* Tests and Javadoc are written before/with code.  
* All pull requests include tests for the added or modified code.
* Master is never broken.  If broken, it is fixed immediately.
* Always check for new changes in master to resolve merge conflicts locally before committing them.
* All changes are built and tested before they are committed.
* Continuous integration successfully builds and tests pull requests for master branch always.
* All commits with more than 1 line of change include a task/issue number.
* All Java dependencies in pom.xml.

## Plan

Epics planned for this release.

* Plan trips in the state of Colorado
* Load button
* Save button
* Display map
* Show itinerary
* Adjust itinerary
* Adjust trip order

*Include a discussion of planning decisions made based on your velocity from previous sprints.*
* We left some things which were less important so we can focus on team building and organization in the required tasks. Our primary issue has been communications, and learning new technology. This should set us up for a polished demo, and for good growth as a team.

## Metrics

Statistic | Planned | Completed
--- | ---: | ---:
Tasks | 15 | 15
Storypoints | 42 | 42

## Daily Scrums

Date | Tasks done  | Tasks in progress | Impediments 
:--- | :--- | :--- | :--- 
2/6/2018|start point| | 
2/8/2018| | |
2/10/2018| |#89, #88, #105, #83|
2/13/2018| |#89, #88, #105, #83|
2/15/2018| |#83, #105, #84, #106, #108, #89, #82|Familiarizing ourselves with RestAPI
2/17/2018|#84, #105, #83|#106, #108, #82, #91, #89, #107, #109, #88, #87, #92|Learning SVG. Communicating tasks
2/20/2018|#106, #108, #91, #113, #107, #120| |Scaling/converting lat/long in SVG. Testing legDistances giving null pointers. Learning flow of state. Constraints of valid TFFI


## Review

#### Completed epics in Sprint Backlog 
* #72: Able to plan trips in CO
* #73: Users can load a valid JSON file, and we will stop invalid files, letting the user know.
* #74: We load a itinerary and show cumulative distance incrementally as well as each individual distance
* #75: Map loads and draws correctly
* #76: The trip always loads in the right order
* #77: The user can save their trip as a JSON

#### Incomplete epics in Sprint Backlog 
* #78: The user cannot customize their itinerary, besides changing unit
* #79: The starting location is always the first in the file

#### What went well
* Learning Junit and performing test-driven development
* Great communication after a certain point
* Collaboration on tasks and working thru problems as a team
* Greatest Circle Distance calculation and all of its components including leg distances and converting dms to degrees
* SVG rendering

#### Problems encountered and resolutions
* We tried to develop on windows, and resolved to set up dual boots
* Rendering starting place twice carried over to subsequent file loads resulting in rendering many more locations than the file contained. Resolved by doing these steps very explicitly and step by step in our JS code. Two lines turned into ten.
* Load button was hard to implement and we had multiple versions which appeared to work but did not. Resolved to get much help from a TA, ultimately using onload. 

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we will change this time | Have people accessible to talk about their code/PRs/ideas. More tests before pushing to master | Use ZenHub board to know who's doing which task | Be responsive on slack
What we did well | Pair program or solve problems in a collaborative fashion | Prioritize tasks | Using IntelliJ and Slack better
What we need to work on | Communicating in a timely manner, express our problems to each other | Test our code before PR | Junit, Zenhub use
What we will change next time | Express problems to each other when we struggle with tasks | Test our code before PR | Write tests before methods in Junit, assign tasks in Zenhub and move them through pipelines
