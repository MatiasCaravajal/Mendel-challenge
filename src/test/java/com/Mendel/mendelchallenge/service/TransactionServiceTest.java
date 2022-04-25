package com.Mendel.mendelchallenge.service;

import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.domain.Transaction;
import com.mendel.mendelchallenge.exception.ParentIdNotFoundException;
import com.mendel.mendelchallenge.repository.TransactionRepositoryImp;
import com.mendel.mendelchallenge.service.TransactionService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {
  TransactionRepositoryImp repository = mock(TransactionRepositoryImp.class);
  TransactionService target = new TransactionService(repository);

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void when_saveTransaction_then_success() {
    Long id = 1l;
    String type = "test";
    double amount = 100;
    Long parentId = null;
    doNothing().when(repository).save(any());

  target.save(id, type, amount, parentId);
    verify(repository, times(1)).save(any());
  }

  @Test()
  public void when_notExistRelatedTransaction_then_throwException() {
    exceptionRule.expect(ParentIdNotFoundException.class);
    exceptionRule.expectMessage(ErrorCode.PARENT_ID_NOT_FOUND.getErrorMessage());

    Long id = 1l;
    String type = "test";
    double amount = 100;
    Long parentId = 99L;

    target.save(id, type, amount, parentId);
  }

  @Test()
  public void when_existRelatedTransaction_then_success() {

    Long id = 1l;
    String type = "test";
    double amount = 100;
    Long parentId = 10l;

    Transaction transaction = mock(Transaction.class);
    when(repository.getTransactionById(parentId)).thenReturn(Optional.of(transaction));
    target.save(id, type, amount, parentId);
    verify(repository, times(1)).save(any(Transaction.class));
  }

  @Test
  public void when_GetTransactionIdsByType_then_returnList() {
    String type = "Test";
    Transaction tx1 = Transaction.builder()
            .id(1L)
            .type(type)
            .amount(100)
            .parentId(null)
            .build();
    Transaction tx2 = Transaction.builder()
            .id(10L)
            .type(type)
            .amount(100)
            .parentId(1l)
            .build();

    when(repository.getTransactionsByType(type)).thenReturn(List.of(tx1, tx2));

    List<Long> result = target.getTransactionIdsByType(type);
    verify(repository, times(1)).getTransactionsByType(type);
    Assert.assertEquals(result, List.of(tx1.getId(), tx2.getId()));
  }

  @Test
  public void when_sumRelatedTransaction_then_returnDouble() {
    String type = "Test";
    long id = 1l;
    Transaction tx1 = Transaction.builder()
            .id(id)
            .type(type)
            .amount(1000)
            .parentId(null)
            .build();
    Transaction tx2 = Transaction.builder()
            .id(10L)
            .type(type)
            .amount(5000)
            .parentId(id)
            .build();
    Transaction tx3 = Transaction.builder()
            .id(10L)
            .type(type)
            .amount(500)
            .parentId(id)
            .build();
    Transaction tx4 = Transaction.builder()
            .id(10L)
            .type(type)
            .amount(2000)
            .parentId(id)
            .build();

    when(repository.getTransactionById(id)).thenReturn(Optional.of(tx1));
    when(repository.getTransactionsByParentId(id)).thenReturn(List.of(tx2, tx3, tx4));

    double result = target.getSumRelatedTransactions(id);
    double total = tx1.getAmount() + tx2.getAmount() + tx3.getAmount() + tx4.getAmount();

    verify(repository, times(1)).getTransactionById(id);
    verify(repository, times(1)).getTransactionsByParentId(id);

    Assert.assertTrue(result == total);
  }

  @Test
  public void when_sumRelatedTransactionWithoutParent_then_returnDouble() {
    String type = "Test";
    long id = 1l;
    Transaction tx1 = Transaction.builder()
            .id(id)
            .type(type)
            .amount(1000)
            .parentId(null)
            .build();

    when(repository.getTransactionById(id)).thenReturn(Optional.of(tx1));
    when(repository.getTransactionsByParentId(id)).thenReturn(List.of());

    double result = target.getSumRelatedTransactions(id);

    verify(repository, times(1)).getTransactionById(id);
    verify(repository, times(1)).getTransactionsByParentId(id);

    Assert.assertTrue(result == tx1.getAmount());
  }

}
