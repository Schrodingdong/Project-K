package com.schrodingdong.quotemanagerservice.controller;

import com.mongodb.client.model.UpdateManyModel;
import com.schrodingdong.quotemanagerservice.model.QuoteModel;
import com.schrodingdong.quotemanagerservice.model.SaveQuoteParams;
import com.schrodingdong.quotemanagerservice.model.UpdateQuoteParams;
import com.schrodingdong.quotemanagerservice.service.QuoteService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote")
@AllArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @PostMapping("/save")
    public ResponseEntity<?> saveQuote(@RequestBody @Validated SaveQuoteParams quoteParams) {
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
