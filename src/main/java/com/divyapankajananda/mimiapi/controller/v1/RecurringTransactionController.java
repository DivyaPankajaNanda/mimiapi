package com.divyapankajananda.mimiapi.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.CustomExceptionDto;
import com.divyapankajananda.mimiapi.dto.RecurringTransactionRequestDto;
import com.divyapankajananda.mimiapi.entity.RecurringTransaction;
import com.divyapankajananda.mimiapi.service.RecurringTransactionService;
import com.divyapankajananda.mimiapi.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_V1_PREFIX+"recurring_transaction")
@Tag(name = "Recurring Transaction")
public class RecurringTransactionController {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    @Operation(summary = "Save recurring transaction.")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecurringTransaction.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> saveUserRecurringTransaction(@RequestBody @Valid RecurringTransactionRequestDto recurringTransactionRequestDto) {
        RecurringTransaction recurringTransaction = recurringTransactionService.saveUserRecurringTransaction(recurringTransactionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recurringTransaction);
    }

    @GetMapping("/")
    @Operation(summary = "Get all recurring transactions for provided budget.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RecurringTransaction.class))))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> findAllUserTransactionsByBudgetId(@RequestParam int offset,@RequestParam int size) {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Page<RecurringTransaction> recurringTransaction = recurringTransactionService.findAllUserRecurringTransactionsWithPagination(currentUserId, offset, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(recurringTransaction);
    }

    @PutMapping("/{transactionid}")
    @Operation(summary = "Update recurring transaction.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecurringTransaction.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> updateUserTransaction(@PathVariable("transactionid") UUID transactionId, @RequestBody @Valid RecurringTransactionRequestDto recurringTransactionRequestDto) {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        RecurringTransaction recurringTransaction = recurringTransactionService.updateUserRecurringTransaction(currentUserId, transactionId, recurringTransactionRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(recurringTransaction);
    }

    @DeleteMapping("/{transactionid}")
    @Operation(summary = "Delete recurring transaction.")
    @ApiResponse(responseCode = "204", description = "Success,No content.", content = @Content())
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> deleteUserTransaction(@PathVariable("transactionid") UUID transactionId) {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        
        recurringTransactionService.deleteUserRecurringTransaction(currentUserId, transactionId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
    
}
