package com.example.consoleApp.repository;

import com.example.consoleApp.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void ItShouldCheckIfItemExistsById() {
        // given
        Long id = 1L;
        Item item = new Item("pen", 10);
        underTest.save(item);

        // when
        boolean exists = underTest.itemExists(id);

        // then
        assertThat(exists).isTrue();

    }

    @Test
    void ItShouldCheckIfItemNotExists() {
        // given
        Long id = 1L;

        // when
        boolean exists = underTest.itemExists(id);

        // then
        assertThat(exists).isFalse();
    }

}