package net.mischung.breadandshampoo.test;

import net.mischung.breadandshampoo.model.ListItem;

import java.util.Map;

class ListItemAssertions {

    private ListItemAssertions() {
        // utility class; no instances
    }

    static void assertItemEquals(ListItem expectedItem, Object actualItemPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> item = (Map<String, Object>) actualItemPayload;
            String failMessage = String.format("Item ID %d differs in response", expectedItem.getId());
            org.junit.jupiter.api.Assertions.assertEquals(expectedItem.getItem(), item.get("item"), failMessage);
        } catch (ClassCastException e) {
            org.junit.jupiter.api.Assertions.fail("Couldn't deserialize single item in response", e);
        }
    }
}
