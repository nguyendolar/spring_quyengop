package com.java.spring_donations.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_donation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDonation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "money")
    private int money;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "donation_id",referencedColumnName = "id")
    private Donation donation;

    @Column(name = "created")
    private String createdAt;
}
