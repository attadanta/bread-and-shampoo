package net.mischung.breadandshampoo.service;

import net.mischung.breadandshampoo.model.ListItem;

import java.util.Objects;

/**
 * An entry in a list item store.
 */
public class ManagedListItem {

    private final String owner;
    private final ListItem data;
    private final boolean deleted;

    public ManagedListItem(ListItem data, String owner, boolean deleted) {
        this.data = Objects.requireNonNull(data);
        this.owner = Objects.requireNonNull(owner);
        this.deleted = deleted;
    }

    public int getId() {
        return data.getId();
    }

    public String getOwner() {
        return owner;
    }

    public ListItem getData() {
        return data;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public ManagedListItem withData(ListItem item) {
        return new ManagedListItem(item, this.owner, this.deleted);
    }

    public ManagedListItem withDeletedFlag(boolean deleted) {
        return new ManagedListItem(this.data, this.owner, deleted);
    }

    @Override
    public String toString() {
        return "ManagedListItem{" +
                "owner='" + owner + '\'' +
                ", data=" + data +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagedListItem that = (ManagedListItem) o;
        return deleted == that.deleted && Objects.equals(owner, that.owner) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, data, deleted);
    }

}
