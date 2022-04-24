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

  final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

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
