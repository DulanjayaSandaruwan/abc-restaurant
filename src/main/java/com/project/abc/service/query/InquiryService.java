package com.project.abc.service.query;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.InquiryNotFoundException;
import com.project.abc.dto.query.InquiryDTO;
import com.project.abc.dto.query.UpdateInquiryResponseDTO;
import com.project.abc.model.query.Inquiry;
import com.project.abc.repository.query.InquiryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    public Inquiry createQuery(InquiryDTO inquiryDTO) {
        log.info("create query");
        Inquiry query = Inquiry.init(inquiryDTO);
        return inquiryRepository.save(query);
    }

    public Inquiry getInquiryById(String inquiryId) {
        log.info("Get inquiry by id = {}", inquiryId);
        Optional<Inquiry> optionalInquiry = inquiryRepository.findById(inquiryId);
        Check.throwIfEmpty(optionalInquiry, new InquiryNotFoundException("Inquiry not found with Id : " + inquiryId));
        Inquiry inquiry = optionalInquiry.get();
        return inquiry;
    }

    public Inquiry updateResponse(UpdateInquiryResponseDTO updateInquiryResponseDTO, String inquiryId) {
        log.info("updated inquiry id {}", inquiryId);
        Inquiry inquiry = this.getInquiryById(inquiryId);
        inquiry.setResponse(updateInquiryResponseDTO.getResponse());
        inquiry.setStatus(Inquiry.InquiryStatus.RESPONDED);
        return inquiryRepository.save(inquiry);
    }
}
