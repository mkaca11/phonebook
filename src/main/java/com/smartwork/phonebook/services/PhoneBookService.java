package com.smartwork.phonebook.services;

import com.smartwork.phonebook.models.PhoneEntry;
import com.smartwork.phonebook.repos.PhoneBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PhoneBookService {

    private final PhoneBookRepository phoneBookRepository;

    public PhoneBookService(PhoneBookRepository phoneBookRepository) {
        this.phoneBookRepository = phoneBookRepository;
    }


    public List<PhoneEntry> getAll(String sortBy, String sortDirection) {
        if (sortBy == null) {
            return phoneBookRepository.getAll();
        } else {
            if (sortDirection == null) {
                sortDirection = "asc";
            }
            return phoneBookRepository.getAllSortedBy(sortBy, sortDirection);
        }
    }

    public PhoneEntry getEntry(UUID id) {
        return phoneBookRepository.getPhoneEntry(id);
    }

    public void createPhoneEntry(PhoneEntry entry) {
        entry.setId(UUID.randomUUID());
        phoneBookRepository.create(entry);
    }

    public void delete(UUID id) {
        phoneBookRepository.delete(id);
    }

    public void editPhoneEntry(UUID id, PhoneEntry entry) {
        phoneBookRepository.edit(id, entry);
    }

}
