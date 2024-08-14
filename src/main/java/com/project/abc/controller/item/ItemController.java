package com.project.abc.controller.item;

import com.project.abc.dto.item.ItemDTO;
import com.project.abc.model.item.Item;
import com.project.abc.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/item")
@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create-item")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        itemDTO.validate();
        Item item = itemService.createItem(itemDTO);
        ItemDTO createItemDTO = ItemDTO.init(item);
        return ResponseEntity.ok(createItemDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable String id) {
        Item item = itemService.getItemById(id);
        ItemDTO itemDTO = ItemDTO.init(item);
        return ResponseEntity.ok(itemDTO);
    }
}
