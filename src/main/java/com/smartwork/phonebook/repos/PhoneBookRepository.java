package com.smartwork.phonebook.repos;

import com.smartwork.phonebook.models.PhoneEntry;

import java.util.List;
import java.util.UUID;

public interface PhoneBookRepository {
    PhoneEntry getPhoneEntry(UUID id);
    List<PhoneEntry> getAll();
    List<PhoneEntry> getAllSortedBy(String by,String direction);
    void create(PhoneEntry entry);
    void edit(UUID id,PhoneEntry entry);
    void delete(UUID id);
}
