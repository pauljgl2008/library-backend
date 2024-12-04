package com.growby.library.backend.repository.author.impl;

import com.growby.library.backend.model.entity.Author;
import com.growby.library.backend.repository.author.AuthorJpaRepository;
import com.growby.library.backend.repository.author.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private final AuthorJpaRepository authorJpaRepository;

    @Override
    public boolean isAuthorExists(Long id) {
        return this.authorJpaRepository.existsById(id);
    }

    @Override
    public List<Author> findAll() {
        return (List<Author>) this.authorJpaRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorJpaRepository.findById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.authorJpaRepository.existsByName(name);
    }

    @Override
    public Author save(Author author) {
        return this.authorJpaRepository.save(author);
    }

    @Override
    public void deleteById(Long id) {
        this.authorJpaRepository.deleteById(id);
    }
}
