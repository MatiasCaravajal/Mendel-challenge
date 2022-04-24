package com.Mendel.mendelchallenge.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.domain.Transaction;
import com.mendel.mendelchallenge.exception.ParentIdNotFoundException;
import com.mendel.mendelchallenge.repository.TransactionRepository;
import com.mendel.mendelchallenge.repository.TransactionRepositoryImp;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionRepositoryTest {

    TransactionRepository target = new TransactionRepositoryImp();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void when_saveTransaction_then_success() {
        Long id = 1L;
        String type = "test";
        double amount = 100;
        Long parentId = null;

        Transaction mockTransaction = mock(Transaction.class);
        when(mockTransaction.getId()).thenReturn(id);
        when(mockTransaction.getType()).thenReturn(type);
        when(mockTransaction.getAmount()).thenReturn(amount);
        when(mockTransaction.getParentId()).thenReturn(parentId);

        target.save(mockTransaction);
        Assert.assertTrue(target.getTransactionById(id).isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_saveTransactionWithIdNull_then_throwException() {
        Long id = null;

        Transaction mockTransaction = mock(Transaction.class);
        when(mockTransaction.getId()).thenReturn(id);

        target.save(mockTransaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_saveTransactionWithTypeNull_then_throwException() {
        Long id = 1L;
        String type = null;

        Transaction mockTransaction = mock(Transaction.class);
        when(mockTransaction.getId()).thenReturn(id);
        when(mockTransaction.getType()).thenReturn(type);

        target.save(mockTransaction);
    }

    @Test()
    public void when_saveTransactionWithWrongParentId_then_throwException() {
        Long id = 1L;
        String type = "test";
        double amount = 100;
        Long parentId = 5L;
        exceptionRule.expect(ParentIdNotFoundException.class);
        exceptionRule.expectMessage(
          ErrorCode.PARENT_ID_NOT_FOUND.getErrorMessage());

        Transaction mockTransaction = mock(Transaction.class);
        when(mockTransaction.getId()).thenReturn(id);
        when(mockTransaction.getType()).thenReturn(type);
        when(mockTransaction.getAmount()).thenReturn(amount);
        when(mockTransaction.getParentId()).thenReturn(parentId);

        target.save(mockTransaction);
    }

    @Test()
    public void when_getTransactionByParentId_then_success() {
         long id = 1l;
        Transaction tx1 = Transaction.builder()
          .id(id)
          .type("test")
          .build();
        Transaction tx2 = Transaction.builder()
          .id(10L)
          .type("test")
          .parentId(id)
          .build();
        Transaction tx3 = Transaction.builder()
          .id(15L)
          .type("test")
          .parentId(id)
          .build();

        target.save(tx1);
        target.save(tx2);
        target.save(tx3);

        List<Transaction> result = target.getTransactionsByParentId(tx1.getId());

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.stream().allMatch(x -> x.getParentId().equals(id)));
    }

    @Test()
    public void when_notExistRelatedTransaction_then_returnEmptyList() {

        Transaction tx1 = Transaction.builder()
          .id(1L)
          .type("test")
          .parentId(null)
          .build();
        Transaction tx2 = Transaction.builder()
          .id(10L)
          .type("test")
          .parentId(null)
          .build();
        Transaction tx3 = Transaction.builder()
          .id(15L)
          .type("test")
          .parentId(null)
          .build();

        target.save(tx1);
        target.save(tx2);
        target.save(tx3);

        List<Transaction> result = target.getTransactionsByParentId(tx1.getId());

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test()
    public void when_getTransactionByType_then_success() {
        String type = "test";
        Transaction tx1 = Transaction.builder()
          .id(1L)
          .type(type)
          .build();
        Transaction tx2 = Transaction.builder()
          .id(10L)
          .type(type)
          .build();
        Transaction tx3 = Transaction.builder()
          .id(15L)
          .type("anotherType")
          .build();

        target.save(tx1);
        target.save(tx2);
        target.save(tx3);

        List<Transaction> result =
          target.getTransactionsByType("test");

        Assert.assertNotNull(result);
        Assert.assertTrue(result.stream().allMatch(x -> x.getType().equals(type)));
    }
}
