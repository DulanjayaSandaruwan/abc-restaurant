package com.project.abc.model.item;

import javax.persistence.*;

import com.project.abc.dto.item.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item")
public class Item {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "qty_on_hand")
    private int qtyOnHand;

    @Column(name = "is_discount")
    private Double discountPercentage;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public enum ItemStatus {
        ACTIVE,
        INACTIVE
    }

    public static Item init(ItemDTO dto) {
        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setItemName(dto.getItemName());
        item.setDescription(dto.getDescription());
        item.setUnitPrice(dto.getUnitPrice());
        item.setQtyOnHand(dto.getQtyOnHand());
        item.setDiscountPercentage(dto.getDiscountPercentage());
        item.setStatus(ItemStatus.ACTIVE);
        return item;
    }
}
