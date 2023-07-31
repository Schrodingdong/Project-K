package com.schrodingdong.quotemanagerservice.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "quotes")
@Data
public class QuoteModel {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userEmail;
    private String quote;
    private String bookAuthor;
    private String book;

    public QuoteModel(String userEmail, String quote, String bookAuthor, String book) {
        this.userEmail = userEmail;
        this.quote = quote;
        this.bookAuthor = bookAuthor;
        this.book = book;
    }
}
