package com.schrodingdong.quotemanagerservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveQuoteParams {
    private String userEmail;
    @NotBlank
    private String quote;
    @NotBlank
    private String book;
    private String bookAuthor;
}
