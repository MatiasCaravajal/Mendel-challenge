package com.mendel.mendelchallenge.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.domain.Transaction;
import com.mendel.mendelchallenge.exception.ParentIdNotFoundException;
import org.junit.Assert;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImp implements TransactionRepository {

    private List<Transaction> transactions;

    public TransactionRepositoryImp() {
        transactions = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    public void save(Transaction theTransaction) {
        Assert.assertNotNull(ErrorCode.TRANSACTION_NULL.getErrorMessage(), theTransaction);
        Assert.assertNotNull(ErrorCode.TRANSACTION_ID_NULL.getErrorMessage(), theTransaction.getId());
        Assert.assertNotNull(ErrorCode.TRANSACTION_TYPE_NULL.getErrorMessage(), theTransaction.getType());

        if(theTransaction.getParent_id() != null &&
          !getTransactionByParentId(theTransaction.getParent_id()).isPresent()){
            throw new ParentIdNotFoundException(ErrorCode.PARENT_ID_NOT_FOUND.getErrorMessage());
        }
        transactions.add(theTransaction);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<Transaction> getTransactionById(final Long theId) {
        return transactions.stream()
          .filter(x -> x.getId().equals(theId))
          .findFirst();
    }


    private Optional<Transaction> getTransactionByParentId(final Long theParentId) {
        return transactions.stream()
          .filter(x -> x.getParent_id().equals(theParentId))
          .findFirst();
    }
}
