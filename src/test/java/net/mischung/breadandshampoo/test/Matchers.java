package net.mischung.breadandshampoo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mischung.breadandshampoo.model.ListItem;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

public class Matchers {

    private final ObjectMapper objectMapper;

    public Matchers() {
        this.objectMapper = new ObjectMapper();
    }

    public ResultMatcher bodyMatchesSingleItem(ListItem expectedListItem) {
        return new SingleListItemMatcher(this.objectMapper, expectedListItem);
    }

    public ResultMatcher bodyMatchesItemList(List<ListItem> expectedListItems) {
        return new ListEntriesMatcher(this.objectMapper, expectedListItems);
    }
}
