package net.mischung.breadandshampoo;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.InMemoryManagedListItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BreadAndShampooApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryManagedListItemRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void smokeTest() {
    }

    @Test
    void exchangeEmptyList() throws Exception {
        this.mockMvc.perform(get("/a/list"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{ \"items\": [], \"size\": 0 }", true));
    }

    @Test
    void insertItem() throws Exception {
        this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isOk())
                .andExpect(bodyMatchesSingleItem(new ListItem(1, "bread")));

        this.mockMvc.perform(get("/a/list"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{ size: 1 }", false));
    }

    @AfterEach
    public void clearRepository() {
        this.repository.clear();
    }

    @SuppressWarnings("unchecked")
    private ResultMatcher matchListEntries(List<ListItem> expectedItems) {
        return result -> {
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
                assertItemEquals(expectedItem, actualItem);
            }
        };
    }

    private ResultMatcher bodyMatchesSingleItem(ListItem expectedItem) {
        return result -> {
            byte[] responseBody = result.getResponse().getContentAsByteArray();
            Object actualListItem;
            try {
                actualListItem = this.objectMapper.readValue(responseBody, Object.class);
            } catch (Exception e) {
                Assertions.fail("Couldn't deserialize single item in response", e);
                // Shouldn't be reachable
                return;
            }
            assertItemEquals(expectedItem, actualListItem);
        };
    }

    private void assertItemEquals(ListItem expectedItem, Object actualItemPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> item = (Map<String, Object>) actualItemPayload;
            String failMessage = String.format("Item ID %d differs in response", expectedItem.getId());
            Assertions.assertEquals(expectedItem.getItem(), item.get("item"), failMessage);
        } catch (ClassCastException e) {
            Assertions.fail("Couldn't deserialize single item in response", e);
        }
    }
}
