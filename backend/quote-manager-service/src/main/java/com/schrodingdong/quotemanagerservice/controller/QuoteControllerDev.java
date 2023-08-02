package com.schrodingdong.quotemanagerservice.controller;

import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import com.schrodingdong.quotemanagerservice.model.SaveQuoteParams;
import com.schrodingdong.quotemanagerservice.model.UpdateQuoteParams;
import com.schrodingdong.quotemanagerservice.service.QuoteService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote/dev")
@Profile("dev")
public class QuoteControllerDev {
    private final QuoteService quoteService;
    QuoteControllerDev(QuoteService quoteService){
    	this.quoteService = quoteService;
    }
    @Value("${issuer-bypass}")
    private String issuerBypassSecret;

    @DeleteMapping("/delete/for-user/all")
    public ResponseEntity<?> deleteAllQuotesOfUser(@RequestParam String userEmail) {
        List<QuoteModel> presumablyDeletedQuoteList = quoteService.deleteAllQuotesOfUser(userEmail);
        if (!presumablyDeletedQuoteList.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    "Problem in deleting quote list : " + presumablyDeletedQuoteList.toString());
        }
        return ResponseEntity.ok().body("Quotes deleted Successfully");
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllQuotes() {
        List<QuoteModel> allQuotes = quoteService.getAllQuotes();
        return ResponseEntity.ok().body(allQuotes);
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> saveQuote(@RequestBody @Validated SaveQuoteParams quoteParams){
        QuoteModel savedQuote = quoteService.saveQuote(quoteParams);
        return ResponseEntity.ok().body(savedQuote);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateQuote(@RequestBody UpdateQuoteParams params, @RequestParam String quoteId) {
        // do update
        QuoteModel updatedQuote = quoteService.updateQuote(params, quoteId, issuerBypassSecret);
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
        QuoteModel presumablyDeletedQuote = quoteService.deleteQuoteById(quoteId, issuerBypassSecret);
        if (presumablyDeletedQuote != null) {
            return ResponseEntity.badRequest().body(
                    "Problem in deleting quote : [" + quoteId + "] '" + presumablyDeletedQuote.getQuote() + "'.");
        }
        return ResponseEntity.ok().body("Quote deleted Successfully");
    }
}
