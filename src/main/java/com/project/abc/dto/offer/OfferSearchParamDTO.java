package com.project.abc.dto.offer;

import com.project.abc.model.offer.Offer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Getter
@Setter
@Slf4j
public class OfferSearchParamDTO {
    private String offerName;
    private Double minUnitPrice;
    private Double maxUnitPrice;
    private Offer.OfferStatus status;
    private Instant startDate;
    private Instant endDate;
    private int page;
    private int size;
}
