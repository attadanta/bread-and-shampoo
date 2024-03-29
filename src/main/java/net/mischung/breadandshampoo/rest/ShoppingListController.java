package net.mischung.breadandshampoo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.model.ListManagementException;
import net.mischung.breadandshampoo.service.ItemDoesNotExistException;
import net.mischung.breadandshampoo.service.ManagedListItemRepository;
import net.mischung.breadandshampoo.service.UserShoppingList;
import net.mischung.breadandshampoo.service.WrongItemOwnerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/{userName}/list", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Shopping List", description = "RESTful interface of Bread and Shampoo, Inc.")
public class ShoppingListController {

    // This is neither an access log, nor the only true way to name this variable, but you should get the idea
    private static final Log accessLog = LogFactory.getLog(ShoppingListController.class);

    private final ManagedListItemRepository itemRepository;

    public ShoppingListController(ManagedListItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping()
    @Operation(summary = "View shopping list items", description = "Buy this")
    public ListItems listItems(@PathVariable(name = "userName") String userName) {
        accessLog.info(String.format("Getting list items for `%s'", userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return new ListItems(shoppingList.getItems());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Insert an item")
    public ListItem insertItem(@PathVariable(name = "userName") String userName,
                               @Validated @RequestBody ItemWrite itemWrite) {
        accessLog.info(String.format("Inserting a list item for `%s'", userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return shoppingList.insertItem(itemWrite.getItem());
    }

    @PutMapping(path = "/items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Change an item")
    public ListItem updateItem(@PathVariable(name = "userName") String userName,
                               @PathVariable(name = "id") Integer id,
                               @Validated @RequestBody ItemWrite itemWrite) {
        accessLog.info(String.format("Updating list item %d for `%s'", id, userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return shoppingList.updateItem(id, itemWrite.getItem());
    }

    @DeleteMapping(path = "/items/{id}")
    @Operation(summary = "Delete an item")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "userName") String userName,
                                           @PathVariable(name = "id") Integer itemId) {
        accessLog.info(String.format("Deleting list item %d for `%s'", itemId, userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        shoppingList.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({ ItemDoesNotExistException.class, WrongItemOwnerException.class })
    public ResponseEntity<Void> handleItemAccessException() {
        // Due to time constraints, we don't implement an error representation. The response
        // code is intentional, however. Since list items are globally unique, we don't want
        // to reveal the fact that someone has accessed an item of a different user.
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({ListManagementException.class })
    public ResponseEntity<Void> handleGeneralException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    UserShoppingList getShoppingList(String owner) {
        return new UserShoppingList(this.itemRepository, owner);
    }

}
