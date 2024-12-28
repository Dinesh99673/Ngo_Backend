package com.project.Ngo.service;

import com.project.Ngo.Repository.ContactUsRepository;
import com.project.Ngo.model.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    public ContactUs saveContactUs(ContactUs contactUs) {
        System.out.println(contactUs+"ok");
        return contactUsRepository.save(contactUs);
    }
}
