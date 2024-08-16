package com.project.abc.controller.offer;

import com.project.abc.dto.offer.OfferDTO;
import com.project.abc.model.offer.Offer;
import com.project.abc.model.offer.OfferDetail;
import com.project.abc.service.offer.OfferDetailService;
import com.project.abc.service.offer.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/offer")
@RestController
@Slf4j
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferDetailService offerDetailService;

    @PostMapping("/create-offer")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO) {
        Offer offer = offerService.createOffer(offerDTO);
        List<OfferDetail> offerDetails = offerDetailService.getOfferDetailsByOfferId(offer.getId());
        OfferDTO dto = OfferDTO.initWithOfferDetails(offer, offerDetails);
        return ResponseEntity.ok(dto);
    }
}
