# Venue Ticket Service
## Overview
This project represents a simple system to manage ticket seats for a venue.

The main functionality of the system can be found in [SimpleTicketService](src/main/java/com/zfesseha/ticketservice/services/SimpleTicketService.java). Even though this implementation makes a few assumptions, it's built in such a way that it can easily be extended to accommodate other use cases.

### Approach
This solution uses the principles of immutability and composition. It employs the composition of highly specialized single purpose components to maximize flexibility and maintainability.

### Assumptions (See below on how these can be expanded)
1. Seats always have a row and column numbers associated with them.
2. Seats are ranked using a simple left -> right, front -> back approach. A seat closer to the front is considered better than a seat further to the back. And a seat closer to the left is considered better than another seat in the same row further from the left.
3. Held seats expire based on a simple rule after a set duration.

### Design Considerations
This solution was designed to optimize for fast execution of the methods in [SimpleTicketService](src/main/java/com/zfesseha/ticketservice/services/SimpleTicketService.java) as it is the main requirement of the solution. As such, some of the data structures used are chosen with this consideration in mind.

##### SortedSeatPool
Instead of storing all seats in one data structure and calculating the best available seats when requested, available seats, held seats and reserved seats are stored separately so that available seats can be stored in the **SortedSeatPool** with their pre computed priority order. This makes holding seats relatively fast.

##### Seat availability status
Since a seat can be categorized as available, held or reserved based on the the data structure it belongs to, the seat itself doesn't store its availability information to avoid duplication of information and the potential for inconsistency.

##### Using Comparator vs implementing Comparable
To sort seats based on a priority order, this solution uses the Comparator interface on the SortedSeatPool instead of having Seats implement the Comparable interface. This means Seats are not tied to a particular prioritization logic and the SortedSeatPool can be customized to use any prioritization order.

### Modifying/Expanding assumptions

##### SortedSeatPool
The SortedSeatPool can be configured with other comparators to allow for more sophisticated sorting.

##### ExpirationResolver interface
The ExpirationResolver can be implemented to introduce more advance expiration strategies such as based on the reserving user, time of day or number of seats requested.

##### Disk storage
Even though all entities are stored in memory, this solution follows a DAO hierarchy to allow for easy extension to a disk based storage.


## How to run

### Pre-requisites
* Java 8

##### Build the project

The server can be built using the following command.

```
./gradlew clean build
```

##### Tests

The tests for this project can be run with following command.

```
./gradlew test
```


