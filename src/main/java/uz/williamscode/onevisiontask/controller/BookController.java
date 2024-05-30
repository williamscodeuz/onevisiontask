package uz.williamscode.onevisiontask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.williamscode.onevisiontask.dto.BookDto;
import uz.williamscode.onevisiontask.dto.BookWithTextResponse;
import uz.williamscode.onevisiontask.service.BookService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Boolean> createBook(@Valid @RequestBody BookDto book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @GetMapping("/grouped")
    public ResponseEntity<Map<String, List<BookDto>>> getAllBooksGroupedByAuthor() {
        return ResponseEntity.ok(bookService.getBooksGroupedByAuthor());
    }

    @GetMapping("/with-text/{text}")
    public ResponseEntity<List<BookWithTextResponse>> getAllBooksWithText(@PathVariable String text) {
        return ResponseEntity.ok(bookService.getBooksWithText(text));
    }

}
