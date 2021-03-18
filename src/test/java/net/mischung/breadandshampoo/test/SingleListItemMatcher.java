package net.mischung.breadandshampoo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mischung.breadandshampoo.model.ListItem;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

public class SingleListItemMatcher implements ResultMatcher {

    private final ObjectMapper objectMapper;
    private final ListItem expectedListItem;

    public SingleListItemMatcher(ObjectMapper objectMapper, ListItem expectedListItem) {
        this.objectMapper = objectMapper;
        this.expectedListItem = expectedListItem;
    }

    @Override
    public void match(MvcResult result) {
        byte[] responseBody = result.getResponse().getContentAsByteArray();
        Object actualListItem;
        try {
            actualListItem = this.objectMapper.readValue(responseBody, Object.class);
        } catch (Exception e) {
            Assertions.fail("Couldn't deserialize single item in response", e);
            // Shouldn't be reachable
            return;
        }
        ListItemAssertions.assertItemEquals(expectedListItem, actualListItem);
    }

}
