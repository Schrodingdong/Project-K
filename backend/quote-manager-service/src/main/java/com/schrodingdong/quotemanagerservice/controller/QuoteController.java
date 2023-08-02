package com.schrodingdong.quotemanagerservice.controller;

import com.google.common.net.HttpHeaders;
import com.mongodb.client.model.UpdateManyModel;
import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import com.schrodingdong.quotemanagerservice.model.SaveQuoteParams;
import com.schrodingdong.quotemanagerservice.model.UpdateQuoteParams;
import com.schrodingdong.quotemanagerservice.service.QuoteService;

import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/quote")
@AllArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;
    private final Logger LOG = LoggerFactory.getLogger(QuoteController.class);

    /***
     * Save the quote in the request body
     * @param quoteParams - Quote Content
     * @param userEmail - the user Email
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveQuote(@RequestBody @Validated SaveQuoteParams quoteParams,
    		@RequestHeader("User-Email")String userEmail){
    	// add the email to the params 
    	quoteParams.setUserEmail(userEmail);
    	// save the quote
    	QuoteModel savedQuote;
    	try {
			savedQuote = quoteService.saveQuote(quoteParams);
    	} catch (IllegalArgumentException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok().body(savedQuote);
    	
    }
    

    /**
     * get the  quotes of an user
     * @param userEmail user to get its quote
     */
    @GetMapping("/get")
    public ResponseEntity<?> getUserQuotes(@RequestParam String userEmail) {
        List<QuoteModel> userQuotes = quoteService.getQuotesFromUserEmail(userEmail);
        return ResponseEntity.ok().body(userQuotes);
    }

    /**
     * Update a quote given its ID
     * @param params quote content to update
     * @param quoteId
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateQuote(@RequestBody UpdateQuoteParams params, @RequestParam String quoteId,
    		@RequestHeader("User-Email") String issuerEmail) {
    	QuoteModel updatedQuote;
    	try {
			// do update
			updatedQuote = quoteService.updateQuote(params, quoteId, issuerEmail);
			// did it update ?
			if (updatedQuote == null) {
				return ResponseEntity.internalServerError().body("No quotes of id : '" + quoteId + "'");
			}
		} catch (NotFoundException e) {
			return ResponseEntity.badRequest().body("The quote with the ID : '"+quoteId+"' doesn't exist");
		} catch (UnsupportedOperationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok().body(
                "Updated Quote : " + updatedQuote);
    }

    /**
     * Delete a quote of a user, and verify if the request issuer is the same as the quote owner
     * @param quoteId the id of the quote to delete
     * @param userEmail the request issuer email
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteQuote(@RequestParam String quoteId, 
    		@RequestHeader("User-Email") String issuerEmail) {
    	try {
    		QuoteModel presumablyDeletedQuote = quoteService.deleteQuoteById(quoteId, issuerEmail);
			if (presumablyDeletedQuote != null) {
				return ResponseEntity.badRequest().body(
						"Problem in deleting quote : [" + quoteId + "] '" + presumablyDeletedQuote.getQuote() + "'.");
			}
		} catch (NotFoundException e) {
			return ResponseEntity.badRequest().body("The quote with the ID : '"+quoteId+"' doesn't exist");
		} catch (UnsupportedOperationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok().body("Quote deleted Successfully");
    }
}
