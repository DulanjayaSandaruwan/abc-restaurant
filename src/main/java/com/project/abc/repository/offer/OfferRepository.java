package com.project.abc.repository.offer;

import com.project.abc.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    Optional<Offer> findByOfferNameAndStatusNot(String offerName, Offer.OfferStatus status);
}
