package uz.williamscode.onevisiontask.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.williamscode.onevisiontask.dao.BookRepository;
import uz.williamscode.onevisiontask.dto.BookDto;
import uz.williamscode.onevisiontask.dto.BookWithTextResponse;
import uz.williamscode.onevisiontask.entity.Book;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.getAllBooks("title", "desc").stream()
                .map(book -> {
                    BookDto bookDto = new BookDto();
                    bookDto.setTitle(book.getTitle());
                    bookDto.setAuthor(book.getAuthor());
                    bookDto.setDescription(book.getDescription());
                    return bookDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean createBook(BookDto book) {
        return bookRepository.createBook(mapDtoToBook(book));
    }

    @Override
    public Map<String, List<BookDto>> getBooksGroupedByAuthor() {
        return bookRepository.getAllBooks("title", "asc")
                .stream()
                .map(this::mapBookToDto)
                .collect(Collectors.groupingBy(BookDto::getAuthor));
    }

    @Override
    public List<BookWithTextResponse> getBooksWithText(String text) {
        return bookRepository.getBooksWithText(text, 10);
    }

    private BookDto mapBookToDto (Book book){
        return mapper.map(book,BookDto.class);
    }
    public Book mapDtoToBook (BookDto dto){
        return mapper.map(dto,Book.class);
    }
}
