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
import com.divyapankajananda.mimiapi.dto.RecurringTransferRequestDto;
import com.divyapankajananda.mimiapi.entity.RecurringTransfer;
import com.divyapankajananda.mimiapi.service.RecurringTransferService;
import com.divyapankajananda.mimiapi.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_V1_PREFIX+"recurring_transfer")
@Tag(name = "Recurring Transfer")
public class RecurringTransferController {
    
    @Autowired
    private RecurringTransferService recurringTransferService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    @Operation(summary = "Save recurring transfer.")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecurringTransfer.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> saveUserRecurringTransaction(@RequestBody @Valid RecurringTransferRequestDto recurringTransferRequestDto) {
        RecurringTransfer recurringTransfer = recurringTransferService.saveUserRecurringTransfer(recurringTransferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recurringTransfer);
    }

    @GetMapping("/goal/{goalid}")
    @Operation(summary = "Get all recurring transfers for provided goal.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RecurringTransfer.class))))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> findAllUserRecurringTransferByGoalId(@PathVariable("goalid") @Valid UUID goalId, @RequestParam int offset,@RequestParam int size) {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Page<RecurringTransfer> transfers = recurringTransferService.findAllUserRecurringTransferByGoalIdWithPagination(currentUserId, goalId, offset, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transfers);
    }

    @PutMapping("/{transferid}")
    @Operation(summary = "Update recurring transfer.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecurringTransfer.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> updateUserRecurringTransaction(@PathVariable("transferid") UUID transferId, @RequestBody @Valid RecurringTransferRequestDto recurringTransferRequestDto) {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        RecurringTransfer recurringTransfer = recurringTransferService.updateUserRecurringTransfer(currentUserId, transferId, recurringTransferRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(recurringTransfer);
    }

    @DeleteMapping("/{transferid}")
    @Operation(summary = "Delete recurring transfer.")
    @ApiResponse(responseCode = "204", description = "Success,No content.", content = @Content())
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> deleteUserTransfer(@PathVariable("transferid") UUID transferId) {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        
        recurringTransferService.deleteUserRecurringTransfer(currentUserId, transferId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
