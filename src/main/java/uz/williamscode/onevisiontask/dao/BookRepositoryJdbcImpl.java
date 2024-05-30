package uz.williamscode.onevisiontask.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import uz.williamscode.onevisiontask.dto.BookWithTextResponse;
import uz.williamscode.onevisiontask.entity.Book;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbcImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> getAllBooks(String columnName, String direction) {
        String query = String.format("SELECT * FROM book ORDER BY %s %s;",
                columnName,
                direction);
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Book book = new Book();
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            return book;
        });
    }

    @Override
    public boolean createBook(Book book) {
        String query = "INSERT INTO book(title, author, description) VALUES(?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return jdbcTemplate.update(connection ->
                {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, book.getTitle());
                    preparedStatement.setString(2, book.getAuthor());
                    preparedStatement.setString(3, book.getDescription());
                    return preparedStatement;
                },
                keyHolder) > 0;
    }


    @Override
    public List<BookWithTextResponse> getBooksWithText(String text, Integer limit) {
        String query = String.format(
                """
                        WITH res_table AS(SELECT  author,\s
                        (LENGTH(title) - LENGTH(REPLACE(LOWER(title), '%s', '')))/LENGTH('%s') occurrence \s
                        FROM book)\s
                        SELECT author, sum(occurrence) as occurrence_sum\s
                        FROM res_table\s
                        GROUP BY author\s
                        ORDER BY occurrence_sum DESC LIMIT %d;
                        """, text, text, limit);

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            String author = rs.getString("author");
            Integer occurrence = rs.getInt("occurrence_sum");
            return new BookWithTextResponse(author, occurrence);
        });
    }

}
