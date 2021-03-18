package net.mischung.breadandshampoo.service;

import net.mischung.breadandshampoo.service.ops.DeleteItem;
import net.mischung.breadandshampoo.service.ops.UpdateItem;
import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.model.ListManagementException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class InMemoryManagedListItemRepository implements ManagedListItemRepository {

    private final AtomicInteger idGenerator;
    private final ConcurrentHashMap<Integer, ManagedListItem> store;

    public InMemoryManagedListItemRepository() {
        this(new AtomicInteger(), null);
    }

    public InMemoryManagedListItemRepository(AtomicInteger idGenerator, Map<Integer, ManagedListItem> initialState) {
        this.idGenerator = idGenerator;
        this.store = initialState == null ? new ConcurrentHashMap<>() : new ConcurrentHashMap<>(initialState);
    }

    @Override
    public List<ManagedListItem> listUserItems(String owner) {
        return this.store.values().stream()
                .filter(managedListItem -> managedListItem.getOwner().equals(owner))
                .filter(managedListItem -> !managedListItem.isDeleted())
                .sorted(Comparator.comparingInt(ManagedListItem::getId))
                .collect(Collectors.toList());
    }

    @Override
    public ManagedListItem insertItem(String owner, String item) {
        int id = this.idGenerator.incrementAndGet();
        ManagedListItem managedListItem = new ManagedListItem(new ListItem(id, item), owner, false);
        this.store.put(id, managedListItem);
        return managedListItem;
    }

    @Override
    public ManagedListItem deleteItem(String owner, int itemId) throws ListManagementException {
        return modifyItem(itemId, new DeleteItem(owner));
    }

    @Override
    public ManagedListItem updateItem(String owner, int itemId, String item) throws ListManagementException {
        return modifyItem(itemId, new UpdateItem(owner, item));
    }

    public void reset() {
        this.store.clear();
        this.idGenerator.set(0);
    }

    private ManagedListItem modifyItem(int itemId, BiFunction<Integer, ManagedListItem, ManagedListItem> mutation) {
        try {
            return this.store.compute(itemId, mutation);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ListManagementException) {
                throw (ListManagementException) e.getCause();
            } else {
                throw e;
            }
        }
    }

}
