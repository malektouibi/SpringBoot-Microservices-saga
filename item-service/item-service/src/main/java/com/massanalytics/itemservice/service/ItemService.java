package com.massanalytics.itemservice.service;

import com.massanalytics.coreapis.commands.CreateCartCommand;
import com.massanalytics.itemservice.dto.ItemRequest;
import com.massanalytics.itemservice.dto.ItemResponse;
import com.massanalytics.itemservice.model.Item;
import com.massanalytics.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;

    public void createItem(ItemRequest itemRequest) {
        String itemId = UUID.randomUUID().toString();
        Item item = Item.builder()
                .itemId(itemId)
                .name(itemRequest.getName())
                .quantity(itemRequest.getQuantity())
                .build();
        itemRepository.save(item);
    }

    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(this::mapToItemResponse).toList();
    }

    private Item mapToItem(ItemResponse itemResponse) {
        return Item.builder()
                .itemId(itemResponse.getItemId())
                .name(itemResponse.getName())
                .quantity(itemResponse.getQuantity())
                .build();
    }

    private ItemResponse mapToItemResponse(Item item) {
        return ItemResponse.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .build();
    }
}
