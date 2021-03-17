package net.misschung.breadandshampoo.service.ops;

import net.misschung.breadandshampoo.service.ManagedListItem;

public class DeleteItem extends AbstractItemUpdate {
    public DeleteItem(String owner) {
        super(owner);
    }

    @Override
    public ManagedListItem apply(Integer id, ManagedListItem managedListItem) {
        verifyOwnership(id, managedListItem);
        return managedListItem.withDeletedFlag(true);
    }
}
