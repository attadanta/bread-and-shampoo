package net.mischung.breadandshampoo.service.ops;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.ManagedListItem;

import java.util.Objects;

public class UpdateItem extends AbstractItemUpdate {
    private final String item;

    public UpdateItem(String owner, String item) {
        super(owner);
        this.item = Objects.requireNonNull(item);
    }

    @Override
    public ManagedListItem doApply(Integer itemId, ManagedListItem managedListItem, ListItem originalItemData) {
        return managedListItem.withData(originalItemData.withItem(item));
    }
}
