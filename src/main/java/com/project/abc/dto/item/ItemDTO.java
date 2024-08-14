package com.project.abc.dto.item;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Slf4j
public class ItemDTO extends BaseRequest {
    private String id;

    @Size(max = 40, min = 3, message = "Full name length should be more than 3 and less than 40")
    @NotBlank(message = "Full name is mandatory")
    private String itemName;

    @Size(max = 200, min = 5, message = "Description length should be more than 5 and less than 200")
    @NotBlank(message = "Full name is mandatory")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than zero")
    private double unitPrice;

    @Min(value = 0, message = "Quantity on hand must be non-negative")
    private int qtyOnHand;

    private double discountPercentage;

    private Item.ItemStatus status;

    public static ItemDTO init(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setItemName(item.getItemName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setUnitPrice(item.getUnitPrice());
        itemDTO.setQtyOnHand(item.getQtyOnHand());
        itemDTO.setDiscountPercentage(item.getDiscountPercentage());
        itemDTO.setStatus(item.getStatus());
        return itemDTO;
    }

}
