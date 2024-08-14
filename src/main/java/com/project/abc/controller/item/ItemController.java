package com.project.abc.controller.item;

import com.project.abc.dto.item.ItemDTO;
import com.project.abc.dto.item.ItemSearchParamDTO;
import com.project.abc.dto.item.ItemUpdateDTO;
import com.project.abc.model.item.Item;
import com.project.abc.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        ItemDTO createItemDTO = ItemDTO.initWithCategory(item);
        return ResponseEntity.ok(createItemDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable String id) {
        Item item = itemService.getItemById(id);
        ItemDTO itemDTO = ItemDTO.initWithCategory(item);
        return ResponseEntity.ok(itemDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateUser(
            @RequestBody ItemUpdateDTO itemUpdateDTO,
            @PathVariable String id
    ) {
        itemUpdateDTO.validate();
        Item item = itemService.updateItem(itemUpdateDTO, id);
        ItemDTO itemDTO = ItemDTO.initWithCategory(item);
        return ResponseEntity.ok(itemDTO);
    }

    @GetMapping("/items")
    public ResponseEntity<Page<ItemDTO>> getAllItems(
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) Item.ItemStatus status,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        ItemSearchParamDTO searchParams = new ItemSearchParamDTO();
        searchParams.setItemName(itemName);
        searchParams.setDescription(description);
        searchParams.setStatus(status);
        searchParams.setMinPrice(minPrice);
        searchParams.setMaxPrice(maxPrice);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Page<Item> itemsPage = itemService.getAllItems(searchParams);
        List<ItemDTO> itemDTOs = itemsPage.getContent().stream()
                .map(ItemDTO::initWithCategory)
                .collect(Collectors.toList());
        Page<ItemDTO> itemDTOPage = new PageImpl<>(
                itemDTOs,
                PageRequest.of(page, size),
                itemsPage.getTotalElements()
        );
        return ResponseEntity.ok(itemDTOPage);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable String itemId) {
        Item item = itemService.deleteItem(itemId);
        return ResponseEntity.ok(ItemDTO.initWithCategory(item));
    }
}
