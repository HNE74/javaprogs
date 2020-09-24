package de.hne.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hne.data.BookEntity;

@Repository
public interface BookRepo extends JpaRepository<BookEntity,Long> {
}