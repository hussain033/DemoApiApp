package com.example.consoleApp.service;

import com.example.consoleApp.model.Item;
import com.example.consoleApp.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private ItemRepository itemRepository;
    private StoreService underTest;


    @BeforeEach
    void setUp() {
        // autoClosable = MockitoAnnotations.openMocks(this);
        underTest = new StoreServiceImpl(itemRepository);
    }


    @Test
    void canGetItemById() {
        // given
        Long id = 123L;
        Item item = new Item("pen", 5);

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        // when
        Item item1 = underTest.getItemById(id);

        // then
        assertThat(item1).isEqualTo(item);

    }

    @Test
    void ShouldThrowForGetItemById() {
        // given
        Long id = 12L;

        // when
        // then
        assertThatThrownBy(() -> underTest.getItemById(id)).isInstanceOf(NullPointerException.class).hasMessage("Item not found");


    }

    @Test
    void canListAllItem() {
        // when
        underTest.listItem();
        // then
        verify(itemRepository).findAll();
    }

    @Test
    void canAddAItem() {
        // given
        Long id = 1L;
        Item item = new Item("pen", 10);

        // when
        underTest.addItem(item);

        // then
        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());

        Item capturedItem = itemArgumentCaptor.getValue();
        assertThat(capturedItem).isEqualTo(item);

    }

    @Test
    void canDeleteAItem() {
        // given
        Long id = 1L;

        // when
        underTest.deleteItem(id);

        // then
        ArgumentCaptor<Long> itemArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(itemRepository).deleteById(itemArgumentCaptor.capture());

    }
}