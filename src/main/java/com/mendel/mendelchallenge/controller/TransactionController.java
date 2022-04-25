package com.mendel.mendelchallenge.controller;

import com.mendel.mendelchallenge.controller.dto.ErrorResponseDto;
import com.mendel.mendelchallenge.controller.dto.TransactionDto;
import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

  /**
   * The transaction service, cannot be null.
   */
  final TransactionService transactionService;

  /**
   * Custom constructor for dependency injection and instance attributes
   * initialization.
   *
   * @param transactionService The transaction service, cannot be null.
   */
  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  /**
   * Receives a request and save a transaction.
   *
   * @param transactionId the transaction id cannot be null.
   * @param transactionDto the transaction dto, cannot be null.
   *
   * @return a map indicating the state, otherwise a {@link ErrorResponseDto} with information about the error.
   */
  @PutMapping("/{transactionId}")
  public ResponseEntity<Object> save(@PathVariable final Long transactionId,
                                     @RequestBody final TransactionDto transactionDto) {

    Assert.notNull(transactionId, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());
    Map<String, String> response;
    try {
      transactionService.save(transactionId, transactionDto.getType(),
              transactionDto.getAmount(), transactionDto.getParentId());
    } catch (Exception ex) {
      return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
    response = new HashMap<>();
    response.put("status", "ok");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Receives a request and retrieve all transaction IDs for the type received by parameter.
   *
   * @param type the type, cannot be null or empty.
   *
   * @return a list with transaction ids, otherwise a {@link ErrorResponseDto} with information about the error.
   */
  @GetMapping("/types/{type}")
  public ResponseEntity<Object> getTransactionIdsByType(@PathVariable final String type) {

    List<Long> transactionIds;
    try {
       transactionIds = transactionService.getTransactionIdsByType(type);

    } catch (Exception ex) {
      return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(transactionIds, HttpStatus.OK);
  }

  /**
   * Receives a request and retrieves a double value that represents the sum between the amounts of the related transactions.
   *
   * @param transactionId the transaction id, cannot be null.
   *
   * @return a double value with the sum between the amounts of the related transactions, otherwise a {@link ErrorResponseDto} with information about the error.
   */
  @GetMapping("/sum/{transactionId}")
  public ResponseEntity<Object> getSumRelatedTransactions(@PathVariable final long transactionId) {

    double sum;
    Map<String, Double> response;
    try {
      sum = transactionService.getSumRelatedTransactions(transactionId);

    } catch (Exception ex) {
      return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
    response = new HashMap<>();
    response.put("sum", sum);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
