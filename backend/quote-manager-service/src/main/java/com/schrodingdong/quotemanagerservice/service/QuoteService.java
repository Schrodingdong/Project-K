package com.schrodingdong.quotemanagerservice.service;

import com.schrodingdong.quotemanagerservice.Repository.QuoteRepository;
import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import com.schrodingdong.quotemanagerservice.model.SaveQuoteParams;
import com.schrodingdong.quotemanagerservice.model.UpdateQuoteParams;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuoteService {
    private final QuoteRepository quoteRepository;

    /***
     * Get All quotes of a user
     * 
     * @param userEmail
     * @return list of quotes of that user
     */
    public List<QuoteModel> getQuotesFromUserEmail(String userEmail) {
        return quoteRepository.findByUserEmail(userEmail);
    }

    /***
     * Get all quotes
     * 
     * @return list of all quotes
     */
    public List<QuoteModel> getAllQuotes() {
        return quoteRepository.findAll();
    }

    /***
     * Save the quote given the params
     * 
     * @param params with user email, quote, book author, and book
     * @return The saved quote
     */
    public QuoteModel saveQuote(SaveQuoteParams params) throws IllegalArgumentException {
    	if (params.getUserEmail() == null || params.getUserEmail().isEmpty() || params.getUserEmail().isBlank()) {
    		throw new IllegalArgumentException("User Email is Empty");
    	}
        return quoteRepository.save(new QuoteModel(
                params.getUserEmail(),
                params.getQuote(),
                params.getBookAuthor(),
                params.getBook()));
    }

    /***
     * Update the quote given a specific id and params to changes
     * 
     * @param params  with quote, book, bookAuthor (non essential)
     * @param quoteId the id of the quote to target
     * @return null if there aren't any quotes with that id, otherwise returns the
     *         updated quote
     */
    public QuoteModel updateQuote(UpdateQuoteParams params, String quoteId) {
        // find it
        QuoteModel q = quoteRepository.findById(quoteId).orElse(null);
        if (q == null)
            return null;
        // do update
        if (params.getQuote() != null && !params.getQuote().isEmpty() && !params.getQuote().isBlank()) {
            q.setQuote(params.getQuote());
        }
        if (params.getBook() != null && !params.getBook().isEmpty() && !params.getBook().isBlank()) {
            q.setQuote(params.getBook());
        }
        if (params.getBookAuthor() != null && !params.getBookAuthor().isEmpty() && !params.getBookAuthor().isBlank()) {
            q.setQuote(params.getBookAuthor());
        }
        // save and return
        return quoteRepository.save(q);
    }

    /***
     * Delete a quote given its id
     * 
     * @param quoteId id of the quote to delete
     * @return
     */
    public QuoteModel deleteQuoteById(String quoteId) {
        quoteRepository.deleteById(quoteId);
        // Verify if its really deleted
        QuoteModel q = quoteRepository.findById(quoteId).orElse(null);
        return q;
    }

    /***
     * Delete all quotes of a user
     * 
     * @param userEmail email of the user
     * @return list of quotes that were deleted
     */
    public List<QuoteModel> deleteAllQuotesOfUser(String userEmail) {
        quoteRepository.deleteByUserEmail(userEmail);
        // verify if its really deleted
        List<QuoteModel> qList = quoteRepository.findByUserEmail(userEmail);
        return qList;
    }
}
