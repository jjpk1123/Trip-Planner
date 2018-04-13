# Sprint 4 - *t17* - *Quixotic Quetzals*

## Goal

### Worldwide!
### Sprint Leader: *Mike Smith*

## Definition of Done

* Web application deployed on the production server (kiwis.cs.colostate.edu).
* Sprint Review and Restrospectives completed (sprint.md).
* Product Increment release `v4.0` created on GitHub with appropriate version number and name, a description based on the Release Notes template, and the archived files.
* Version in pom.xml should be `<version>4.0</version>`.
* Javadoc and unit tests for public methods.
* Tests for database and REST APIs.
* Coverage at least (60%) overall and for each class.
* Keep code maintainability at an (A) average, which means keep every individual at an (A)

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

* *#210 Zoom and pan the map*
* *#206 Plan trips outside of Colorado*
* *#142 Distance Units*
* *#212 Distance Unit Configuration*
* *#140 Shorter trips (2-opt)*
* *#209 Filter Query searches*
* *#79  Let the user choose a new starting location*
* *#137 Let the user reverse the order of the trip*
* *#141 Branding*
* *#134 Clean code*
* *#135 Code coverage*

*Include a discussion of planning decisions made based on your velocity from previous sprints.*

* Based on our previous sprints, we estimated that the three of us could tackle ~40 story points for sprint4, and have adjusted our backlog/icebox configuration accordingly. Accomplishing tasks based on priority is important, so the epics (above) are, in fact, in order of precedence.

## Metrics

Statistic | Planned | Completed
--- | ---: | ---:
Tasks |  *17*   | *17*
Story Points |  *36*  | *36*

## Daily Scrums

Date | Tasks done  | Tasks in progress | Impediments
:--- | :--- | :--- | :---
*3/27* | none | *#216 #217 #223* | none
*3/29* | *#216 #217* | *#223 #226 #220 #221 #236* | none
*4/01* | *#220 #221* | *#223 #226 #249 #251* | none
*4/03* | *#137 #226 #251* | *#211 #223 #249* | Waited to PR #211 until #223 is merged. The team decided we would rather let him focus on 2-opt, instead of sorting through some confusing merge conflicts.
*4/05* | *#222 #249 #250* | *#223 #249 #251* | none
*4/08* | *#223* | *#208 #224 #225* | none
*4/10* | *#207 #227 #229 #266* | *#209* | none
 

## Review

#### Completed epics in Sprint Backlog 
* #141:  Client is now branded with CSU green.
* #212:  Config now sends supported distance units from server to client.
* #142:  Client's distance buttons upgraded to a dropdown. Also, the user can use the modal to create custom "user defined" units directly from the web page.
* #206:  Client generates a world Svg, instead of just Colorado. (and "clips" the lines crossing the L/R border).
* #140:  Client supports 2-opt & Server can now compute 2-opt. Then 2-opt was revised to run even faster!
* #137:  User can "Reverse Trip" with the click of a button.

#### Incomplete epics in Sprint Backlog 
* *#79* : *This epic (Let the user start from a different starting location) is not of top-priority, and there wasn't enough time to get this completed.*
* *#210*: *This epic (Zoom and pan the map) was not of top-priority, because we made a working world SVG this sprint. This seems like a large epic, and was omitted due to uncertainty of the time commitment.*
* *#209*: *This epic (Filtered Searches) was trimmed just before the end of the sprint, because we did not have enough time to get it working by midnight. The other tasks associated with this were fixing the query table, and have been coimpleted.*

#### What went well
* *We started tasks early this sprint, and it has paid off, as we have also fixed quite a bit of leftover bugs from Sprint 3. Some of these don't count as tasks, but we're mostly caught up to the expectations of a team of 4 members.*
* *As suggested, the team switched up the side they were predominantly working on. So, if you've mostly been working on the client side: switch to the server side, and vice versa. This has helped to round out the team, so we all get a solid understanding of what the project is doing on both sides.*
* *Communication on slack has been pretty frequent, and quite helpful when a team member is stuck (due to the switching of sides).*


#### Problems encountered and resolutions
* *2-opt implementation took a lot longer than we originally estimated, but Dave talking about the implementation in class helped clear a few misunderstandings for Ezra. After 2-opt was implemented, revised to run faster, and finalized, it totalled up to over 16 hours for this 10-point epic. After completed, we did not alter the story point estimate to reflect the time commitment; I don't want this to go unnoticed.*
* *Javascript is a peculiar language and we came across many complications not found on Google, so we went into help hours often to help guide our code.*
* *Working in a three-man team while implementing Sprint 4 on top of correcting Sprint 3 bugs was a large time commitment. Each of us had to work extra on code to catch up.*

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we will change this time | When a teammamte comes acrosss an issue, they should annonunmce their issue. | Even more communication on Slack. | Slack
What we did well | Communicate the prioritizing of tasks in order of precedence. | Self-assigning tasks from ZenHub | ZenHub and Slack
What we need to work on | Assigning tasks to an ample amount of estimated story points. | During planning phase, over-estimate the story point conribution. | ZenHub
What we will change next time | Jest unit testing has not been implemented in this group of three. | Unit testing | Jest
