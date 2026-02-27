package com.schoolar.lynx.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "company_social_network")
@Getter
@Setter
public class CompanySocialNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "social_network_id", nullable = false)
    private SocialNetwork socialNetwork;

    @Column(nullable = false)
    private String url;
}
