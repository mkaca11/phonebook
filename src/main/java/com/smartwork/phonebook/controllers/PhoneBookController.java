package com.smartwork.phonebook.controllers;

import com.smartwork.phonebook.models.PhoneEntry;
import com.smartwork.phonebook.services.PhoneBookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/phone-book")
public class PhoneBookController {
    private final PhoneBookService phoneBookService;


    public PhoneBookController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping
    public List<PhoneEntry> getAll(@RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortDirection) {
        return phoneBookService.getAll(sortBy, sortDirection);
    }

    @GetMapping("{id}")
    public PhoneEntry getById(@PathVariable UUID id) {
        return phoneBookService.getEntry(id);
    }

    @PostMapping
    public void create(@RequestBody PhoneEntry entry) {
        phoneBookService.createPhoneEntry(entry);
    }

    @PutMapping("{id}")
    public void edit(@PathVariable UUID id, @RequestBody PhoneEntry entry) {
        phoneBookService.editPhoneEntry(id, entry);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id) {
        phoneBookService.delete(id);
    }


}
