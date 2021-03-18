package net.mischung.breadandshampoo;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.InMemoryManagedListItemRepository;
import net.mischung.breadandshampoo.test.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

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

    private final Matchers matchers = new Matchers();

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

    @AfterEach
    public void clearRepository() {
        this.repository.reset();
    }

}
