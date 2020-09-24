package de.hne.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hne.data.BookEntity;
import de.hne.repo.BookRepo;

@Component
public class BookService {
	
	@Autowired
	BookRepo bookRepo;
	
    public List<BookEntity> getAll() {
        List<BookEntity> entities = bookRepo.findAll();
        return entities;
    }
}
