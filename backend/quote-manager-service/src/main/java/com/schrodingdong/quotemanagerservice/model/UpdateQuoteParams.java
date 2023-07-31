package com.schrodingdong.quotemanagerservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateQuoteParams {
    private String quote;
    private String book;
    private String bookAuthor;
}
