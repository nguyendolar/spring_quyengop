package com.java.spring_donations.repositorys;

import com.java.spring_donations.domain.Donation;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.domain.UserDonation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDonationRepository extends JpaRepository<UserDonation,Integer> {
    UserDonation save(UserDonation userDonation);

    List<UserDonation> findUserDonationByDonation(Donation donation);

    List<UserDonation> findUserDonationByUser(User user);

    UserDonation findUserDonationById(int id);

    void deleteByDonation(Donation donation);
    void deleteByUser(User user);
}
