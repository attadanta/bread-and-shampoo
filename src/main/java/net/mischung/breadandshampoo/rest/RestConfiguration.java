package net.mischung.breadandshampoo.rest;

import net.mischung.breadandshampoo.service.InMemoryManagedListItemRepository;
import net.mischung.breadandshampoo.service.ManagedListItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {

    @Bean
    public ManagedListItemRepository managedListItemRepository() {
        return new InMemoryManagedListItemRepository();
    }

}
