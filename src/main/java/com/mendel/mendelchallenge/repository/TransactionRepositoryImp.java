package com.mendel.mendelchallenge.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.domain.Transaction;
import com.mendel.mendelchallenge.exception.ParentIdNotFoundException;
import org.springframework.util.Assert;
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
        Assert.notNull(theTransaction, ErrorCode.TRANSACTION_NULL.getErrorMessage());
        Assert.notNull(theTransaction.getId(), ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());
        Assert.hasLength(theTransaction.getType(), ErrorCode.TRANSACTION_TYPE_NULL.getErrorMessage());

        if (theTransaction.getParent_id() != null &&
          !getTransactionById(theTransaction.getParent_id()).isPresent()) {
            throw new ParentIdNotFoundException(
              ErrorCode.PARENT_ID_NOT_FOUND.getErrorMessage());
        }
        transactions.add(theTransaction);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<Transaction> getTransactionById(final Long theId) {
        Assert.notNull(theId, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());

        return transactions.stream()
          .filter(x -> x.getId() == theId)
          .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactionsByParentId(final Long theId) {
        Assert.notNull(theId, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());
        return transactions.stream()
          .filter(x -> x.getParent_id() == theId)
          .collect(Collectors.toList());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactionsByType(final String theType) {
        Assert.hasLength(theType, ErrorCode.TRANSACTION_TYPE_NULL.getErrorMessage());
        return transactions.stream()
          .filter(x -> x.getType() == theType)
          .collect(Collectors.toList());
    }
}
