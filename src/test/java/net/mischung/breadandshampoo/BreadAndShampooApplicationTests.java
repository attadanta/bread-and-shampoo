package net.mischung.breadandshampoo;

import net.mischung.breadandshampoo.service.InMemoryManagedListItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void smokeTest() {
    }

    @Test
    void exchangeEmptyList() throws Exception {
        this.mockMvc.perform(get("/a/list"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{ items: [], size: 0 }", true));
    }

    @AfterEach
    public void clearRepository() {
        this.repository.clear();
    }

}
