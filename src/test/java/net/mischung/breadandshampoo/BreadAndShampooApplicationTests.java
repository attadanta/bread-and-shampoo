package net.mischung.breadandshampoo;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.InMemoryManagedListItemRepository;
import net.mischung.breadandshampoo.test.Matchers;
import net.mischung.breadandshampoo.test.Responses;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BreadAndShampooApplicationTests {

    private final Matchers matchers = new Matchers();
    private final Responses responses = new Responses();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryManagedListItemRepository repository;

    @Test
    void smokeTest() {
    }

    @Test
    void exchangeEmptyList() throws Exception {
        this.mockMvc.perform(get("/a/list"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"items\": [], \"size\": 0 }", true));
    }

    @Test
    void insertItem() throws Exception {
        ListItem expectedItem = new ListItem(1, "bread");

        this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isOk())
                .andExpect(this.matchers.bodyMatchesSingleItem(expectedItem));

        this.mockMvc.perform(get("/a/list"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(matchers.bodyMatchesItemList(
                        Collections.singletonList(expectedItem)
                ));
    }

    @Test
    void insertItemMangledInput() throws Exception {
        this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"tutti\": \"frutti\" }"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void insertLargeItem() throws Exception {
        String format = String.format("{ \"item\": \"%s\"", longString());
        this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content(format))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateItem() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isOk()).andReturn();

        ListItem listItem = this.responses.readItem(mvcResult);

        this.mockMvc.perform(
                put("/a/list/items/" + listItem.getId())
                        .contentType("application/json")
                        .content("{ \"item\": \"shampoo\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(listItem.getId()))
                .andExpect(jsonPath("$.item").value("shampoo"))
                .andReturn();

        List<ListItem> expectedResult = Collections.singletonList(new ListItem(listItem.getId(), "shampoo"));

        this.mockMvc.perform(
                get("/a/list"))
                .andExpect(status().isOk())
                .andExpect(this.matchers.bodyMatchesItemList(expectedResult));
    }

    @Test
    void updateAnItemWithLongEntry() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isOk()).andReturn();

        ListItem listItem = this.responses.readItem(mvcResult);

        String content = String.format("{ \"item\": \"%s\" }", longString());

        this.mockMvc.perform(
                put("/a/list/items/" + listItem.getId())
                        .contentType("application/json")
                        .content(content))
                .andDo(log())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateNonExistingItem() throws Exception {
        this.mockMvc.perform(
                put("/a/list/items/1")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAnotherUsersItem() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isOk())
                .andReturn();

        ListItem listItem = this.responses.readItem(result);

        this.mockMvc.perform(
                put("/b/list/items/" + listItem.getId())
                        .contentType("application/json")
                        .content("{ \"item\": \"shampoo\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteItem() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isOk()).andReturn();

        ListItem listItem = this.responses.readItem(mvcResult);

        this.mockMvc.perform(delete("/a/list/items/" + listItem.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        this.mockMvc.perform(
                get("/a/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(0));
    }

    @Test
    void deleteNonExistingItem() throws Exception {
        this.mockMvc.perform(
                delete("/a/list/items/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAnotherUsersItem() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/a/list")
                        .contentType("application/json")
                        .content("{ \"item\": \"bread\" }"))
                .andExpect(status().isOk())
                .andReturn();

        ListItem listItem = this.responses.readItem(result);

        this.mockMvc
                .perform(delete("/b/list/items/" + listItem.getId()))
                .andExpect(status().isNotFound());
    }

    @AfterEach
    public void clearRepository() {
        this.repository.reset();
    }

    String longString() {
        return RandomStringUtils.random(666, true, false);
    }

}
