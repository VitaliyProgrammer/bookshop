package org.example.service;

import java.util.List;
import org.example.dto.BookDto;
import org.example.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto createBookRequestDto);

    void deleteBook(Long id);
}
