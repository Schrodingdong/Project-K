package com.schrodingdong.quotemanagerservice.service;

import com.schrodingdong.quotemanagerservice.Repository.QuoteRepository;
import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import com.schrodingdong.quotemanagerservice.model.SaveQuoteParams;
import com.schrodingdong.quotemanagerservice.model.UpdateQuoteParams;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final Logger LOG = LoggerFactory.getLogger(QuoteService.class);
    public QuoteService(QuoteRepository quoteRepository) {
    	this.quoteRepository = quoteRepository;
	}
    @Value("${issuer-bypass}")
    private String issuerBypassSecret;

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
     * Update the quote given a specific id and params to changes, and check if the request issuer is the same as the quote owner
     * 
     * @param params  with quote, book, bookAuthor (non essential)
     * @param quoteId the id of the quote to target
     * @param issuerEmail the email of the request issuer
     * @throws NotFoundException if the quoteId isn't associated to a quote
     * @throws UnsupportedOperationException if the request issuer is different than the quote owner
     * @return null if there aren't any quotes with that id, otherwise returns the
     *         updated quote
     */
    public QuoteModel updateQuote(UpdateQuoteParams params, String quoteId, String issuerEmail) throws
    		NotFoundException, UnsupportedOperationException{
        // find it and check ownership
        QuoteModel q = quoteRepository.findById(quoteId)
    			.orElseThrow(() -> new NotFoundException("Quote of ID : '" + quoteId + "' doesn't exist."));
        if (!issuerEmail.equals(issuerBypassSecret) && !q.getUserEmail().equals(issuerEmail)) {
			throw new UnsupportedOperationException("The quote isn't owned by the request issuer");
        }
    	
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
     * Delete a quote given its id, and check if the issuer is the same as the quote owner
     * 
     * @param quoteId id of the quote to delete
     * @param issuerEmail the email of the request issuer
     * @throws NotFoundException if the quoteId isn't associated to a quote
     * @throws UnsupportedOperationException if the request issuer is different than the quote owner
     */
    public QuoteModel deleteQuoteById(String quoteId, String issuerEmail) throws NotFoundException, UnsupportedOperationException{
    	// verify ownership 
    	QuoteModel q = quoteRepository.findById(quoteId)
    			.orElseThrow(() -> new NotFoundException("Quote of ID : '" + quoteId + "' doesn't exist."));
        if (!issuerEmail.equals(issuerBypassSecret) && !q.getUserEmail().equals(issuerEmail)) {
			throw new UnsupportedOperationException("The quote isn't owned by the request issuer");
        }
    	// delete if otherwise
        quoteRepository.deleteById(quoteId);
        // Verify if its really deleted
        q = quoteRepository.findById(quoteId).orElse(null);
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
