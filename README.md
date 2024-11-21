# Bread and Shampoo

A shopping list management API for the Web 2.0.

> At Bread and Shampoo, Inc., we almost never forget your shopping list items.

## Assumptions

After looking at the problem statement, I came up with the following assumptions that informed the
implementation:

* In this project, we are focusing on the requirement list items being identifiable by an ID. It can
  be argued that there's an important business case behind it, and therefore we will make list
  entries top-level entities.
* The service makes a minimal effort towards multi-tenancy, however insecure. That means that the
  list management takes place under the `/{userName}` path.
* No management of list of lists. Once the user has created their account, they can manage one and
  only one shopping list.
* List items fit a `VARCHAR255` column. If your shopping list entries are longer than that, you are
  doing it wrong.
* No UI also means no HTML representation of the shopping list. Content negotiation is therefore off
  the table.
* A Swagger UI, however, would be desirable.
* No persistence layer. I don't think I can manage setting that up within the time constraints (and
  frankly, it's no fun).

## Getting Up and Running

This is a Spring Boot App, which can be built and executed by gradle:

`./gradlew bootRun`

Once running, you can use the Swagger UI at [/swagger-ui.html][1] to get to know the API.

To run the tests, do

```shell
./gradlew test
```

## Code Organization

The only controller of the API is called `ShoppingListController`. It could serve as a good starting point for a code 
review. The Javadoc documentation is sparse, but the package comments should explain the general structure.

[1]: http://localhost:8080/swagger-ui.html