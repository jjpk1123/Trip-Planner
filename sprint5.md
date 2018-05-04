# Sprint 5 - *t17* - *Quixotic Quetzals*

## Goal

### Interoperability and User Defined Design
### Sprint Leader: *Julien Pecquet*

## Definition of Done

* Web application deployed on the production server (kiwis.cs.colostate.edu).
* Sprint Review and Restrospectives completed (sprint.md).
* Product Increment release `v5.0` created on GitHub with appropriate version number and name, a description based on the Release Notes template, and the archived files.
* Version in pom.xml should be `<version>5.0</version>`.
* Javadoc and unit tests for public methods.
* Coverage at least (60%) overall.
* Keep code maintainability at an (B) average.

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

* #209 Filtered Searches
* #272 TFFI Updates
* #273 Interop
* #274 Shorter trips
* #275 Staff Page
* #278 Update the User Interface

* Our last two sprints we have completed around 35-40 storypoints as a team, and we believe this is a good estimation of our ability. However, we believe that previously we were overestimating our ability, thus underestimating our storypoints. I believe we are probably doing more like 50 storypoints but underestimating how long things take and not adjusting it. For this sprint, we decided to go with the strategy of promising less than we think we can do, and adding things if our velocity is faster than expected.

## Metrics

Statistic | Planned | Completed
--- | ---: | ---:
Tasks |  *17*   | *17*
Story Points |  *27*  | *27*

## Daily Scrums

Date | Tasks done  | Tasks in progress | Impediments
:--- | :--- | :--- | :---
4/17/2018 | | #293, #213, #208 | 
4/19/2018 | #293, #208 | #213, #208, #294, #295, #300 | 
4/22/2018 | #294, #295, #300 | #312, #208, #282 | 
4/24/2018 | #282 | #312, #208,  #296 | 
4/26/2018 | #312, #296  | #285, #286 | 
4/29/2018 | #285, #286  | #287 | 
5/01/2018 |  | #288, #290, #289, #291, #282, #284, #283  | 
5/03/2018 |  #288, #290, #289, #291, #282, #284, #283 |  | 
 
## Review

#### Completed epics in Sprint Backlog 
* #209 Filtered Searches
* #272 TFFI Updates
* #273 Interop
* #274 Shorter trips
* #275 Staff Page

#### Incomplete epics in Sprint Backlog 
* #278 Update the User Interface

#### What went well
We got some things done in the end! Ultimately, we were barely short of our end goal, and actually got all of our storypoints completed.

#### Problems encountered and resolutions
We had a lot of trouble starting working on things, as a team. This is still a problem but was "resolved" by doing many things on the very last day of the sprint. There were pretty large difficulties testing one of our major classes "Optimize.java" which holds everything to do with optimizing a trip. It was extremely messy, so we resolved to clean it up through an inspection and follow up tasks to make it much better. It went from 25% test coverage and F maintainability to 98% coverage and B maintainability.

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we changed this time | We lowered our expected deliverables and storypoints. | We tried to split up the tasks into much smaller parts, many 1 storypoints | Use Zenhub more efficiently.
What we did well | We got things done | We had one person incrementally develop, and submitted many PRs which were reviewed quickly | 
What we need to work on | Communication about what it is we are able to do, and what is holding us back | Timely completion of tasks | 
What we will change next time | Try to be honest about what we can do, and what (in life) is making this difficult. Ask for help! | Commit something new every day or every other day | Use code climate integration in IntelliJ to make sure our commits don't lower maintainability scores and coverage.
