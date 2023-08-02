package com.schrodingdong.quotemanagerservice.controller;

import com.google.common.net.HttpHeaders;
import com.mongodb.client.model.UpdateManyModel;
import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import com.schrodingdong.quotemanagerservice.model.SaveQuoteParams;
import com.schrodingdong.quotemanagerservice.model.UpdateQuoteParams;
import com.schrodingdong.quotemanagerservice.service.QuoteService;
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
    
    @Deprecated
    public ResponseEntity<?> saveQuote_depr(@RequestBody @Validated SaveQuoteParams quoteParams,
    		@RequestHeader("Authorization") String jwtToken) {
    	jwtToken = jwtToken.substring(7);
    	LOG.info("The jwt token : " + jwtToken);

    	// get the email 
    	String userEmail;
    	String[] token = jwtToken.split("\\.");
    	if (token.length != 3) {
            return ResponseEntity.badRequest().body("Invalid Jwt Format");
        }
    	String payload = new String(Base64.getUrlDecoder().decode(token[1]));
    	LOG.info("The payload of the jwt token : " + payload);
    	
    	String subBoilerPlate = "\"sub\":\"";
    	int subStartIndex = payload.indexOf(subBoilerPlate)+subBoilerPlate.length();
    	LOG.info(Integer.toString(subStartIndex));
    	int subEndIndex = payload.indexOf("\"", subStartIndex);
    	LOG.info(Integer.toString(subEndIndex));
    	userEmail = payload.substring(subStartIndex, subEndIndex);
    	LOG.info(userEmail);
    	quoteParams.setUserEmail(userEmail);
    	
    	// save the quote
        QuoteModel savedQuote = quoteService.saveQuote(quoteParams);
        return ResponseEntity.ok().body(savedQuote);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUserQuotes(@RequestParam String userEmail) {
        List<QuoteModel> userQuotes = quoteService.getQuotesFromUserEmail(userEmail);
        return ResponseEntity.ok().body(userQuotes);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuote(@RequestBody UpdateQuoteParams params, @RequestParam String quoteId) {
        // do update
        QuoteModel updatedQuote = quoteService.updateQuote(params, quoteId);
        // did it update ?
        if (updatedQuote == null) {
            return ResponseEntity.internalServerError().body("No quotes of id : '" + quoteId + "'");
        }
        // return
        return ResponseEntity.ok().body(
                "Updated Quote : " + updatedQuote);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteQuote(@RequestParam String quoteId) {
        QuoteModel presumablyDeletedQuote = quoteService.deleteQuoteById(quoteId);
        if (presumablyDeletedQuote != null) {
            return ResponseEntity.badRequest().body(
                    "Problem in deleting quote : [" + quoteId + "] '" + presumablyDeletedQuote.getQuote() + "'.");
        }
        return ResponseEntity.ok().body("Quote deleted Successfully");
    }
}
