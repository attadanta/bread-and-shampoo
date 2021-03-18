package net.mischung.breadandshampoo.service;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.model.ShoppingList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadOnlyShoppingList implements ShoppingList {
    private final List<ListItem> items;

    public ReadOnlyShoppingList(List<ListItem> items) {
        this.items = items == null ? Collections.emptyList() : new ArrayList<>(items);
    }

    @Override
    public List<ListItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    @Override
    public ListItem updateItem(int itemId, String item) {
        throw new UnsupportedOperationException("This list is read only");
    }

    @Override
    public ListItem insertItem(String item) {
        throw new UnsupportedOperationException("This list is read only");
    }

    @Override
    public void deleteItem(int itemId) {
        throw new UnsupportedOperationException("This list is read only");
    }
}
