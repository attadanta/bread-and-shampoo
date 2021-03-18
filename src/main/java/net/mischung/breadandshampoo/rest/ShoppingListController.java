package net.mischung.breadandshampoo.rest;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.ManagedListItemRepository;
import net.mischung.breadandshampoo.service.UserShoppingList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShoppingListController {

    // This is neither an access log, nor the only true way to name this variable, but you should get the idea
    private static final Log accessLog = LogFactory.getLog(ShoppingListController.class);

    private final ManagedListItemRepository itemRepository;

    public ShoppingListController(ManagedListItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/{userName}/list")
    public ListItems listItems(@PathVariable(name = "userName") String userName) {
        accessLog.info(String.format("Getting list items for `%s'", userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return new ListItems(shoppingList.getItems());
    }

    @PostMapping("/{userName}/list")
    public ListItem insertItem(@PathVariable(name = "userName") String userName, @RequestBody InsertItem insertItem) {
        accessLog.info(String.format("Inserting a list item for `%s'", userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return shoppingList.insertItem(insertItem.getItem());
    }

    @PutMapping("/{userName}/list/items/{id}")
    public ListItem updateItem(@PathVariable(name = "userName") String userName, @RequestParam(name="id") Integer id, @RequestBody UpdateItem updateItem) {
        accessLog.info(String.format("Updating list item %d for `%s'", id, userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        return shoppingList.updateItem(id, updateItem.getItem());
    }

    @DeleteMapping("/{userName}/list/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "userName") String userName, @RequestParam(name="id") Integer itemId) {
        accessLog.info(String.format("Deleting list item %d for `%s'", itemId, userName));
        UserShoppingList shoppingList = getShoppingList(userName);
        shoppingList.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    UserShoppingList getShoppingList(String owner) {
        return new UserShoppingList(this.itemRepository, owner);
    }

}
