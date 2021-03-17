# Bread and Shampoo

A shopping list management API for the Web 2.0.

## Assumptions

After looking at the problem statement, I came up with the following assumptions that informed the
implementation:

* In this project, we are focusing on the requirement list items being identifiable by an ID. It can
  be argued that there's an important business case behind it and therefore we will make list
  entries top-level entities.
* The service makes a minimal effort towards multi-tenancy, however insecure. That means that the
  list management takes place under the `/{userName}` endpoint.
* No management of list of lists. Once the user has created their account, they can manage one and
  only one shopping list.
* List items fit a `VARCHAR255` column. If your shopping list entries are longer than that, you are
  doing it wrong.
* No UI also means no HTML representation of the shopping list. Content negotiation is therefore off
  the table.
* A Swagger UI, however, would be desirable.
* No persistence layer. I don't thing I can manage setting that up within the time constraints (and
  frankly, it's no fun).

## Running

This is a Spring Boot Application.
