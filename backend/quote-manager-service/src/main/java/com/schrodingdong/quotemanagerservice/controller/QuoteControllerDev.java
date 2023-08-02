package com.schrodingdong.quotemanagerservice.controller;

import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import com.schrodingdong.quotemanagerservice.model.SaveQuoteParams;
import com.schrodingdong.quotemanagerservice.service.QuoteService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote/dev")
@Profile("dev")
@AllArgsConstructor
public class QuoteControllerDev {
    private final QuoteService quoteService;

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
}
