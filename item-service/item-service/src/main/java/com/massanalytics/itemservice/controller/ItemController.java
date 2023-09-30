package com.massanalytics.itemservice.controller;

import com.massanalytics.itemservice.dto.ItemRequest;
import com.massanalytics.itemservice.dto.ItemResponse;
import com.massanalytics.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@RequestBody ItemRequest itemRequest) {
        log.info("Controller");
        itemService.createItem(itemRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
    }
}
