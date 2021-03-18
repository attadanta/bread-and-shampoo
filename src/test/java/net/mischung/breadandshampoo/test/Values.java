package net.mischung.breadandshampoo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.model.ShoppingList;
import net.mischung.breadandshampoo.service.ReadOnlyShoppingList;
import org.junit.jupiter.api.Assertions;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class exists solely as a reminder to pick a different JVM language when working with JSON.
 */
public class Values {

    private final ObjectMapper objectMapper;

    public Values() {
        // Time constraints => no injection
        this.objectMapper = new ObjectMapper();
    }

    public ShoppingList readList(MvcResult result) throws IOException {
        MockHttpServletResponse response = result.getResponse();
        String rawContent = response.getContentAsString();

        @SuppressWarnings("unchecked")
        Map<String, Object> rawRoot = this.objectMapper.readValue(rawContent, Map.class);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rawItems = (List<Map<String, Object>>) rawRoot.get("items");

        List<ListItem> items = rawItems.stream().map(entry -> {
            try {
                return new ListItem((int) entry.get("id"), (String) entry.get("item"));
            } catch (Exception e) {
                Assertions.fail("Not a list: " + rawContent);
                return null;
            }
        }).collect(Collectors.toList());

        return new ReadOnlyShoppingList(items);
    }

    public ListItem readItem(MvcResult result) throws IOException {
        MockHttpServletResponse response = result.getResponse();

        @SuppressWarnings("unchecked")
        Map<String, Object> map = this.objectMapper.readValue(response.getContentAsByteArray(), Map.class);

        try {
            return new ListItem((int) map.get("id"), (String) map.get("item"));
        } catch (Exception e) {
            Assertions.fail("Not a list item: " + response.getContentAsString());
            return null;
        }
    }
}
