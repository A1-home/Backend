package com.example.Security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountPreviewSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private Long leadId = 0L;

    private Long quotationId = 0L;

    private Long paymentPlanId;

    private Long termId;

    @Column(columnDefinition = "text")  // Store as a string in the database
    private String additionalPageIdsString;  // Store as a colon-delimited string

    private String templateName = "Single Table";

    private Long rowsToShowId = 0L;



    private Long columnsToShowId = 0L;

    private Long DescriptionToShowId = 0L;

    @Transient
    private List<Long> additionalPageIds;  // Temporary in-memory field for the List<Long>

    // Convert List<Long> to colon-delimited string before persisting or updating
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (additionalPageIds != null && !additionalPageIds.isEmpty()) {
            this.additionalPageIdsString = additionalPageIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));  // Convert List<Long> to a colon-separated string
        } else {
            this.additionalPageIdsString = "";  // Default to empty string if null
        }
    }

    // Convert colon-delimited string back to List<Long> when reading from the database
    @PostLoad
    public void postLoad() {
        if (additionalPageIdsString != null && !additionalPageIdsString.isEmpty()) {
            String[] ids = additionalPageIdsString.split(",");
            this.additionalPageIds = new ArrayList<>();
            for (String id : ids) {
                try {
                    this.additionalPageIds.add(Long.parseLong(id));  // Convert each part to Long
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Error converting string to List<Long>", e);
                }
            }
        } else {
            this.additionalPageIds = new ArrayList<>();  // Default to empty list if null or empty
        }
    }
}
