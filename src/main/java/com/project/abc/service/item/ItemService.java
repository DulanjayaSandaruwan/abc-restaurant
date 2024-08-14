package com.project.abc.service.item;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.ItemNotFoundException;
import com.project.abc.dto.item.ItemDTO;
import com.project.abc.model.item.Item;
import com.project.abc.repository.item.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(ItemDTO itemDTO) {
        log.info("create item {}", itemDTO.getItemName());
        Item item = Item.init(itemDTO);
        return itemRepository.save(item);
    }

    public Item getItemById(String itemId) {
        log.info("Get item by id = {}", itemId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        Check.throwIfEmpty(itemOptional, new ItemNotFoundException("Item not found with Id : " + itemId));
        Item item = itemOptional.get();
        return item;
    }
}
