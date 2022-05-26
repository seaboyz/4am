### ERD - Entity-Relationship Diagram
#### V1(05/05/2022)
![](/backend/images/database/ERD-Kenneth.png)
#### V2(05/06/2022)
![](/backend/images/database/ERD-TEAM-V1.png)
#### TEAM V3(05/09/2022)
![](/backend/images/database/ERD-Fatemeh.png)
#### MINE
![](/backend/images/database/ERD.png) 

### Pitfalls
#### 1. Inadequate Planning
* No solid database entity relationship diagram
#### 2. Poor Communication
* Diffrent intepretations of the user story
So our team never agreed on the definition of the cart.
* Is cart a unpaid order?
* Should cartItems persist in memory/front-end browser/database? Or don't persist at all?
* How a about logined user?
* Should users persist in memory/front-end browser/database? Or don't persist at all?
#### 3. Lack of git best practices
##### Good
* FEATURE BRANCHES 
  The advantage of a feature branch separate from master is that you are not breaking anything, you can commit often, merge in the checked-in code of others and push your code to your branch thus keeping it safe and reducing the risk of losing your work.

##### Bad
* NO FEATURE BRANCHES
  It's hard work independently, without break other people's code.Many people work on the same code, and it's hard to keep track of the changes.
  when you merge your code to master, you are breaking other people's code.
#### 4. lack of testing
##### six sigma