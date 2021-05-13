package com.smartwork.phonebook.repos.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.smartwork.phonebook.models.PhoneEntry;
import com.smartwork.phonebook.repos.PhoneBookRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FilePhoneBookRepository implements PhoneBookRepository {

    private List<PhoneEntry> data = new ArrayList<>();
    private final ResourceLoader resourceLoader;

    public FilePhoneBookRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    private void loadData() {
        try {
            File folder = new File("C:\\smartworkapp\\data\\");
            if (!folder.exists()) folder.mkdirs();
            File resource = new File("C:\\smartworkapp\\data\\phonebook.json");
            if (resource.exists()) {
                Gson gson = new Gson();
                data = gson.fromJson(new FileReader(resource),
                        new TypeToken<List<PhoneEntry>>() {
                        }.getType());
            }
        } catch (IOException e) {
            System.out.println("Error getting data");
        }
    }

    private void saveData() {
        try {
            File resource = new File("C:\\smartworkapp\\data\\phonebook.json");
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Writer writer = new FileWriter(resource);
            gson.toJson(data, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving");
        }


    }

    @Override
    public PhoneEntry getPhoneEntry(UUID id) {
        loadData();
        return data.stream().filter(it -> it.getId() == id).findAny().orElse(null);
    }

    @Override
    public List<PhoneEntry> getAll() {
        loadData();
        return data;
    }

    @Override
    public List<PhoneEntry> getAllSortedBy(String by, String direction) {
        loadData();
        return data.stream().sorted((e1, e2) -> {
            if (direction.equals("asc")) {
                if (by.equals("firstName")) {
                    return e1.getFirstName().compareTo(e2.getFirstName());
                } else if (by.equals("lastName")) {
                    return e1.getLastName().compareTo(e2.getLastName());
                }
            } else if (direction.equals("desc")) {
                if (by.equals("firstName")) {
                    return e2.getFirstName().compareTo(e1.getFirstName());
                } else if (by.equals("lastName")) {
                    return e2.getLastName().compareTo(e1.getLastName());
                }

            }
            return 0;
        }).collect(Collectors.toList());
    }

    @Override
    public void create(PhoneEntry entry) {
        loadData();
        data.add(entry);
        saveData();
    }

    @Override
    public void edit(UUID id, PhoneEntry entry) {
        loadData();
        data.forEach(record -> {
            if (record.getId() == id) {
                if (record.getId() == id) {
                    record.setFirstName(entry.getFirstName());
                    record.setLastName(entry.getLastName());
                    record.setNumber(entry.getNumber());
                    record.setType(entry.getType());
                }
            }
        });
        saveData();
    }

    @Override
    public void delete(UUID id) {
        loadData();
        data.removeIf(record -> record.getId() == id);
        saveData();
    }
}
