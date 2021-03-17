package net.misschung.breadandshampoo.service.ops;

import net.misschung.breadandshampoo.model.ListItem;
import net.misschung.breadandshampoo.service.ManagedListItem;

public class UpdateItem extends AbstractItemUpdate {
    private final String item;

    public UpdateItem(String owner, String item) {
        super(owner);
        this.item = item;
    }

    @Override
    public ManagedListItem apply(Integer id, ManagedListItem managedListItem) {
        ListItem originalData = verifyOwnership(id, managedListItem);
        return managedListItem.withData(originalData.withItem(item));
    }
}
