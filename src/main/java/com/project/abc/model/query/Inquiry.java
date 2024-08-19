package com.project.abc.model.query;

import com.project.abc.dto.query.InquiryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "inquiry")
public class Inquiry {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "response")
    private String response;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public enum InquiryStatus {
        PENDING,
        RESPONDED
    }

    public static Inquiry init(InquiryDTO dto) {
        Inquiry query = new Inquiry();
        query.setId(UUID.randomUUID().toString());
        query.setSubject(dto.getSubject());
        query.setMessage(dto.getMessage());
        query.setResponse(dto.getResponse());
        query.setStatus(InquiryStatus.PENDING);
        return query;
    }
}
