package com.java.spring_donations.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "donation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "money")
    private int money;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "donation")
    private List<UserDonation> userDonations;

    @Column(name = "created")
    private String createdAt;
}
