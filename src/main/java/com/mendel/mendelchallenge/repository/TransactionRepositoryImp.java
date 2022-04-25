package com.mendel.mendelchallenge.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.domain.Transaction;
import com.mendel.mendelchallenge.exception.ParentIdNotFoundException;
import com.mendel.mendelchallenge.exception.TransactionAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImp implements TransactionRepository {

  private List<Transaction> transactions;
  
  @Autowired
  public TransactionRepositoryImp() {
    transactions = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   */
  public void save(Transaction theTransaction) {
    Assert.notNull(theTransaction, ErrorCode.TRANSACTION_NULL.getErrorMessage());
    Assert.notNull(theTransaction.getId(), ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());
    Assert.hasLength(theTransaction.getType(), ErrorCode.TRANSACTION_TYPE_NULL_OR_EMPTY.getErrorMessage());

    if(getTransactionById(theTransaction.getId()).isPresent()) {
      throw new TransactionAlreadyExistException(ErrorCode.TRANSACTION_ALREADY_EXIST.getErrorMessage());
    }
    transactions.add(theTransaction);
  }


  /**
   * {@inheritDoc}
   */
  public Optional<Transaction> getTransactionById(final Long theId) {
    Assert.notNull(theId, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());

    return transactions.stream()
            .filter(x -> x.getId().equals(theId))
            .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Transaction> getTransactionsByParentId(final Long theId) {
    Assert.notNull(theId, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());
    return transactions.stream()
            .filter(x -> theId.equals(x.getParentId()))
            .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Transaction> getTransactionsByType(final String theType) {
    Assert.hasLength(theType, ErrorCode.TRANSACTION_TYPE_NULL_OR_EMPTY.getErrorMessage());
    return transactions.stream()
            .filter(x -> theType.equalsIgnoreCase(x.getType()))
            .collect(Collectors.toList());
  }
}
