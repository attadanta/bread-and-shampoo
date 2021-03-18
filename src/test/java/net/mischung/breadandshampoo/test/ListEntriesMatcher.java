package net.mischung.breadandshampoo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mischung.breadandshampoo.model.ListItem;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Map;

class ListEntriesMatcher implements ResultMatcher {

    private final ObjectMapper objectMapper;
    private final List<ListItem> expectedItems;

    ListEntriesMatcher(ObjectMapper objectMapper, List<ListItem> expectedItems) {
        this.objectMapper = objectMapper;
        this.expectedItems = expectedItems;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void match(MvcResult result) {
        byte[] responseBody = result.getResponse().getContentAsByteArray();

        Map<String, Object> listRepresentation;
        try {
            listRepresentation = this.objectMapper.readValue(responseBody, Map.class);
        } catch (Exception e) {
            Assertions.fail("Couldn't deserialize shopping list in response", e);
            // Shouldn't be reachable
            return;
        }

        List<Object> actualItemList;
        try {
            actualItemList = (List<Object>) listRepresentation.get("items");
        } catch (ClassCastException e) {
            Assertions.fail("Couldn't deserialize list element in response", e);
            // Shouldn't be reachable
            return;
        }

        int expectedListSize = actualItemList.size();
        Assertions.assertEquals(expectedItems.size(), expectedListSize,
                String.format("List in response has differing size than the expected %d", expectedListSize));

        for (int i = 0; i < expectedItems.size(); i++) {
            ListItem expectedItem = expectedItems.get(i);
            Object actualItem = actualItemList.get(i);
            ListItemAssertions.assertItemEquals(expectedItem, actualItem);
        }
    }

}
