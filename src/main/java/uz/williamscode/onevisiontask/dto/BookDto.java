package uz.williamscode.onevisiontask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    private String description;
}
