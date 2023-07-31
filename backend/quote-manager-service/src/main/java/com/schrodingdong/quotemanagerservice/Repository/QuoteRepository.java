package com.schrodingdong.quotemanagerservice.Repository;

import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QuoteRepository extends MongoRepository<QuoteModel, String> {
    List<QuoteModel> findByUserEmail(String userEmail);
    List<QuoteModel> findByBook(String book);
    List<QuoteModel> findByBookAuthor(String bookAuthor);
    void deleteByUserEmail(String userEmail);
}
