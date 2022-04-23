package com.Mendel.mendelchallenge.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
public class repositoryTest {

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
        when(mockTransaction.getParent_id()).thenReturn(parentId);

        target.save(mockTransaction);
        Assert.assertTrue(target.getTransactionById(id).isPresent());
    }

    @Test(expected = AssertionError.class)
    public void when_saveTransactionWithIdNull_then_throwException() {
        Long id = null;

        Transaction mockTransaction = mock(Transaction.class);
        when(mockTransaction.getId()).thenReturn(id);

        target.save(mockTransaction);
    }

    @Test(expected = AssertionError.class)
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
        exceptionRule.expectMessage(ErrorCode.PARENT_ID_NOT_FOUND.getErrorMessage());

        Transaction mockTransaction = mock(Transaction.class);
        when(mockTransaction.getId()).thenReturn(id);
        when(mockTransaction.getType()).thenReturn(type);
        when(mockTransaction.getAmount()).thenReturn(amount);
        when(mockTransaction.getParent_id()).thenReturn(parentId);

        target.save(mockTransaction);
    }
}
