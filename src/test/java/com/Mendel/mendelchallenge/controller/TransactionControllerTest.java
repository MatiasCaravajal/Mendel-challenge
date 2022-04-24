package com.Mendel.mendelchallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.mendelchallenge.controller.TransactionController;
import com.mendel.mendelchallenge.controller.dto.ErrorResponseDto;
import com.mendel.mendelchallenge.controller.dto.TransactionDto;
import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.domain.Transaction;
import com.mendel.mendelchallenge.repository.TransactionRepository;
import com.mendel.mendelchallenge.repository.TransactionRepositoryImp;
import com.mendel.mendelchallenge.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private TransactionRepositoryImp transactionRepository;

  @Test
  public void when_saveTransaction_then_success() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();

    TransactionDto tx1 = TransactionDto.builder()
            .type("test")
            .amount(100)
            .build();

    MvcResult mvcResult = mvc.perform(put("/transactions/{transactionId}",1l)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tx1)))
            .andExpect(status().isOk())
            .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();

    Map<String, String> expecctedOutput = new HashMap<>();
    expecctedOutput.put("status","ok");

    Assert.assertTrue(responseBody.equalsIgnoreCase(objectMapper.writeValueAsString(expecctedOutput)));

    verify(transactionRepository, times(1)).save(any(Transaction.class));
  }

  @Test
  public void when_saveTransactionWithAmountZero_then_returnErrorDto() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();

    TransactionDto tx1 = TransactionDto.builder()
            .type("test")
            .build();

    MvcResult mvcResult = mvc.perform(put("/transactions/{transactionId}",1l)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tx1)))
            .andExpect(status().isBadRequest())
            .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();

    ErrorResponseDto expectedError = new ErrorResponseDto(ErrorCode.AMOUNT_CANNOT_BE_ZERO.getErrorMessage()
            , HttpStatus.BAD_REQUEST.value());

    Assert.assertTrue(responseBody.equalsIgnoreCase(objectMapper.writeValueAsString(expectedError)));
    verify(transactionRepository, times(0)).save(any(Transaction.class));
  }

  @Test
  public void when_saveTransactionWithNonExistingRelatedTransaction_then_returnErrorDto() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();

    TransactionDto tx1 = TransactionDto.builder()
            .type("test")
            .amount(100)
            .parentId(99L)
            .build();

    MvcResult mvcResult = mvc.perform(put("/transactions/{transactionId}",1l)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tx1)))
            .andExpect(status().isBadRequest())
            .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();

    ErrorResponseDto expectedError = new ErrorResponseDto(ErrorCode.PARENT_ID_NOT_FOUND.getErrorMessage()
            , HttpStatus.BAD_REQUEST.value());

    Assert.assertTrue(responseBody.equalsIgnoreCase(objectMapper.writeValueAsString(expectedError)));
    verify(transactionRepository, times(0)).save(any(Transaction.class));
  }
}
