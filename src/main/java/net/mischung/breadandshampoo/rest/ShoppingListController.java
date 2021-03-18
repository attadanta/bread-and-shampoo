package net.mischung.breadandshampoo.rest;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.ItemDoesNotExistException;
import net.mischung.breadandshampoo.service.ManagedListItemRepository;
import net.mischung.breadandshampoo.service.UserShoppingList;
import net.mischung.breadandshampoo.service.WrongItemOwnerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/{userName}/list", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingListController {

    // This is neither an access log, nor the only true way to name this variable, but you should get the idea
    private static final Log accessLog = LogFactory.getLog(ShoppingListController.class);

    private final ManagedListItemRepository itemRepository;

    public ShoppingListController(ManagedListItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping()
    public ListItems listItems(@PathVariable(name = "userName") String userName) {
        accessLog.info(String.format("Getting list items for `%s'", userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return new ListItems(shoppingList.getItems());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ListItem insertItem(@PathVariable(name = "userName") String userName, @Validated @RequestBody InsertItem insertItem) {
        accessLog.info(String.format("Inserting a list item for `%s'", userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return shoppingList.insertItem(insertItem.getItem());
    }

    @PutMapping(path = "/items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ListItem updateItem(@PathVariable(name = "userName") String userName, @PathVariable(name = "id") Integer id, @Validated @RequestBody UpdateItem updateItem) {
        accessLog.info(String.format("Updating list item %d for `%s'", id, userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return shoppingList.updateItem(id, updateItem.getItem());
    }

    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "userName") String userName, @PathVariable(name = "id") Integer itemId) {
        accessLog.info(String.format("Deleting list item %d for `%s'", itemId, userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        shoppingList.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({ ItemDoesNotExistException.class, WrongItemOwnerException.class })
    public ResponseEntity<Void> handleException() {
        // Due to time constraints, we don't implement an error representation.
        // The response code is intentional, however.
        // We don't want to reveal the fact that someone has accessed an item of a different user.
        return ResponseEntity.notFound().build();
    }

    UserShoppingList getShoppingList(String owner) {
        return new UserShoppingList(this.itemRepository, owner);
    }

}
