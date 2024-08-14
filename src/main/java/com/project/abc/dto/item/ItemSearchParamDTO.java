package com.project.abc.dto.item;

import com.project.abc.model.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ItemSearchParamDTO {
    private String itemName;
    private String description;
    private Item.ItemStatus status;
    private Double minPrice;
    private Double maxPrice;
    private int page;
    private int size;
}
