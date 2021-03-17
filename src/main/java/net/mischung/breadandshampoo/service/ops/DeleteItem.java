package net.mischung.breadandshampoo.service.ops;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.ManagedListItem;

public class DeleteItem extends AbstractItemUpdate {

    public DeleteItem(String owner) {
        super(owner);
    }

    @Override
    public ManagedListItem doApply(Integer itemId, ManagedListItem managedListItem, ListItem originalItemData) {
        return managedListItem.withDeletedFlag(true);
    }

}
