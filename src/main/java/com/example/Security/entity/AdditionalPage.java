package com.example.Security.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AdditionalPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "additional_page_name", nullable = false)
    private String addtionalPageName;

    private String objectKey;


}
