package uz.williamscode.onevisiontask.service;

import org.springframework.stereotype.Service;
import uz.williamscode.onevisiontask.dto.BookDto;
import uz.williamscode.onevisiontask.dto.BookWithTextResponse;

import java.util.List;
import java.util.Map;

@Service
public interface BookService {
    List<BookDto> getAllBooks();
    boolean createBook(BookDto book);
    Map<String, List<BookDto>> getBooksGroupedByAuthor();
    List<BookWithTextResponse> getBooksWithText(String text);

}
