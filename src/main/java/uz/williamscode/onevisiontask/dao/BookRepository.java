package uz.williamscode.onevisiontask.dao;

import uz.williamscode.onevisiontask.dto.BookWithTextResponse;
import uz.williamscode.onevisiontask.entity.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAllBooks(String columnName, String direction);
    boolean createBook(Book book);
    List<BookWithTextResponse> getBooksWithText(String text, Integer limit);
}
