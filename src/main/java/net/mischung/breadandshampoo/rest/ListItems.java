package net.mischung.breadandshampoo.rest;

import net.mischung.breadandshampoo.model.ListItem;

import java.util.List;
import java.util.Objects;

public class ListItems {

    private List<ListItem> items;
    private int size;

    public ListItems(List<ListItem> items) {
        this.items = Objects.requireNonNull(items);
        this.size = items.size();
    }

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


}
