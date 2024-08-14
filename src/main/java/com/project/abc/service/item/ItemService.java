package com.project.abc.service.item;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.http.ItemNotFoundException;
import com.project.abc.commons.exceptions.item.ItemExType;
import com.project.abc.dto.item.ItemDTO;
import com.project.abc.dto.item.ItemSearchParamDTO;
import com.project.abc.dto.item.ItemUpdateDTO;
import com.project.abc.model.item.Item;
import com.project.abc.repository.item.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if (itemRepository.findByItemName(itemDTO.getItemName()).isPresent()) {
            throw new BadRequestException("item already exist", ItemExType.ITEM_ALREADY_EXIST);
        }
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

    public Item updateItem(ItemUpdateDTO itemUpdateDTO, String itemId) {
        log.info("updated item id {}", itemId);
        Item item = this.getItemById(itemId);
        item.setItemName(itemUpdateDTO.getItemName());
        item.setDescription(itemUpdateDTO.getDescription());
        item.setUnitPrice(itemUpdateDTO.getUnitPrice());
        item.setQtyOnHand(itemUpdateDTO.getQtyOnHand());
        item.setDiscountPercentage(itemUpdateDTO.getDiscountPercentage());
        item = itemRepository.save(item);
        return item;
    }

    public Page<Item> getAllItems(ItemSearchParamDTO searchParams) {
        Pageable pageable = PageRequest.of(searchParams.getPage(), searchParams.getSize());
        return itemRepository.findItems(
                searchParams.getItemName(),
                searchParams.getDescription(),
                searchParams.getStatus(),
                searchParams.getMaxPrice(),
                searchParams.getMinPrice(),
                pageable
        );
    }
}
