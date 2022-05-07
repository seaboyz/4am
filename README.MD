# Project 2 - E-Commerce SPA

## NOTE
Add `hibernate.cfg.xml` to `/backend/src/main/resources`
[A Note About P2](https://github.com/220328-Java-Full-Stack-AWS/Curriculum-Notes/blob/main/P2.md)
![](./images/Screen%20Shot%202022-05-04%20at%209.14.08%20PM.png)


#### ERD - Entity-Relationship Diagram
https://dbdiagram.io/d/62733e857f945876b6bb6479
![](images/db-diagram.png)

#### value object vs entity object
- value object: has no meaning without other object (address,cart)
![](images/Screen%20Shot%202022-05-07%20at%205.37.34%20AM.png)
- entity object: can exist independently (user, order, product)
- edge case: think about a order can be created by a guest(without register as a user)
- so the order has to be independent from user


### relationships
#### one-to-one
- `one user has one cart`
- `one user has one address` 
#### one-to-many
>"As stated in the JPA specification under section 2.9, it's a good practice to mark the many-to-one side as the owning side."
https://www.baeldung.com/hibernate-one-to-many#:~:text=As%20stated%20in%20the%20JPA%20specification%20under%20section%202.9%2C%20it%27s%20a%20good%20practice%20to%20mark%20the%C2%A0many%2Dto%2Done%20side%C2%A0as%20the%20owning%20side.
#### many-to-one
>https://dzone.com/tutorials/java/hibernate/hibernate-example/hibernate-mapping-many-to-one-using-annotations-1.html
#### bidirectional @OneToMany
>https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/

>https://www.youtube.com/watch?v=tciSOIQngig
#### many-to-many
- `cart` and `product`
  `does one cart has many product?`
  `does one product has many carts?`


## Executive Summary
The E-Commerce Single-Page Application will allow consumers to browse, search, and buy products. Users will be able to browse our catelog of products, or search for a specific item, and add these to a cart. Users can register accounts which includes a profile. There are a number of stretch goals to choose from. Please update this text to summarize additional features. This text is fairly generic, your project will be unique, this text should be re-written to reflect your final deliverable.


# Tech Stack
 - Languages
   - Java
   - JavaScript
   - TypeScript
 - Data Persistence
   - PostgreSQL
   - Hibernate
 - AWS
   - RDS
   - S3
   - CodeBuild & CodePipeline
   - ElasticBeanstalk
 - Spring Framework
   - Spring Boot
   - Spring MVC
 - Angular

## Forbidden Abstractions:
 - Spring Data JPA - Spring Data JPA uses Hibernate as an ORM provider. Before we get to that level of abstraction, we will use hibernate directly ourselves.


# Functional Requirements
### Required:
 - Domain objects persisted in relational database via ORM
 - All CRUD functionality accessible via RESTful API
 - Single page web UI to consume RESTful API
 - Workflows to complete all user stories
 - Validate all user input
 - Unit test coverage for service-layer classes

### Stretch Goals:
 - Application is merged, tested, and deployed with a fully functional CI/CD Pipeline

The persistence layer shall use Hibernate ORM to translate between the database and the application server. The API layer shall abstract away the low-level servlets with Spring Web MVC. The client shall use Angular to produce an SPA which is styled to be functional and readable. The server should follow a proper layered architecture, and have adequate unit testing of the service layer. The client and server should communicate in a RESTful manner, and the server should be stateless. 


## User Stories
### Requirements:
#### Guest
 - As a guest, I can register for an account.
 - As a guest, I can log in to my account.

#### User:
 - As a user, I have a profile which I can view.
 - As a user, I can browse products and add them to my cart.
 - As a user, I can remove items from my cart.
 - As a user, I can checkout to purchase the items in my cart.

### Stretch Goals:
 - As a User, I should be able to select an amount of an item to add to my cart as I am adding an item
 - As a User, I should be able to search the product list to better find the item(s) I am interested in
 - As a User, I should be able to see and purchase items that are on sale for a lower price.
 - As a User, I should be able to see a list of featured products on the main page of the application
 - As a User, my session should be maintained until I log out.
 - As a User, I should be able to reset my password.
 - As a User, I should be able to change the color scheme from the normal mode to a dark mode option.
 - As a User, I should receive notifications when a transaction has occurred or a transfer has been completed.

# Submission
### Due Date: Thursday 5/26/2022 at 9:00 AM CST
Your project needs to be pushed into the main branch of your team's P2 repository no later than the due data and time above. Commits after this deadline will not be considered. On the due date there will be a presentation. You will be expected to briefly cover your project, and should be prepared to discuss it with QC.

