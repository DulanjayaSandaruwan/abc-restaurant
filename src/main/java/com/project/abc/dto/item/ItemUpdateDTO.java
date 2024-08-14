package com.project.abc.dto.item;

import com.project.abc.commons.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;

@Getter
@Setter
@Slf4j
public class ItemUpdateDTO extends BaseRequest {
    @Size(max = 40, min = 3, message = "Full name length should be more than 3 and less than 40")
    @NotBlank(message = "Full name is mandatory")
    private String itemName;

    @Size(max = 200, min = 5, message = "Description length should be more than 5 and less than 200")
    @NotBlank(message = "Full name is mandatory")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than zero")
    private Double unitPrice;

    @Min(value = 0, message = "Quantity on hand must be non-negative")
    private int qtyOnHand;

    @DecimalMin(value = "0.0", inclusive = false, message = "Discount percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount percentage must be less than or equal to 100")
    private Double discountPercentage;
}
