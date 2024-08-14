package com.project.abc.repository.item;

import com.project.abc.model.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    Optional<Item> findByItemName(String itemName);

    @Query("SELECT i FROM item i WHERE (:itemName IS NULL OR i.itemName LIKE %:itemName%) " +
            "AND (:description IS NULL OR i.description LIKE %:description%) " +
            "AND (:status IS NULL OR i.status = :status) " +
            "AND (:minPrice IS NULL OR i.unitPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR i.unitPrice <= :maxPrice)")
    Page<Item> findItems(
            @Param("itemName") String itemName,
            @Param("description") String description,
            @Param("status") Item.ItemStatus status,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
