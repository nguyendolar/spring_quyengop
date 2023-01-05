package com.java.spring_donations.services.impl;

import com.java.spring_donations.domain.Donation;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.repositorys.DonationRepository;
import com.java.spring_donations.services.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    DonationRepository donationRepository;

    @Override
    public Page<Donation> findAll(Pageable pageable) {
        return donationRepository.findAll(pageable);
    }

    @Override
    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    @Override
    public Donation findDonationsById(int id) {
        return donationRepository.findDonationsById(id);
    }

    @Override
    public Donation save(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public void delete(int id) {
        donationRepository.deleteById(id);
    }
}
