package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookDto;
import org.example.dto.CreateBookRequestDto;
import org.example.mapper.BookMapper;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toEntity(bookRequestDto);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateBook(Long id, CreateBookRequestDto createBookRequestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id));
        bookMapper.updateBookFromDto(createBookRequestDto, book);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book by id " + id);
        }
        bookRepository.existsById(id);
    }
}
