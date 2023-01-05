package com.java.spring_donations.repositorys;

import com.java.spring_donations.domain.Donation;
import com.java.spring_donations.domain.UserDonation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DonationRepository extends PagingAndSortingRepository<Donation,Integer> {

    Page<Donation> findAll(Pageable pageable);

    List<Donation> findAll();

    Donation findDonationsById(int id);

    void deleteById(int id);

}
