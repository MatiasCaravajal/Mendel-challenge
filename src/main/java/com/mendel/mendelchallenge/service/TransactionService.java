package com.mendel.mendelchallenge.service;

import com.mendel.mendelchallenge.domain.ErrorCode;
import com.mendel.mendelchallenge.domain.Transaction;
import com.mendel.mendelchallenge.exception.ParentIdNotFoundException;
import com.mendel.mendelchallenge.exception.TransactionNotFoundException;
import com.mendel.mendelchallenge.repository.TransactionRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;


/**
 * The transaction service to resolve integration between controller and repository.
 */
@Service
public class TransactionService {
  /**
   * The transaction repository, cannot be null.
   */
  private final TransactionRepositoryImp transactionRepository;

  /**
   * The constructor with mandatory parameters.
   *
   * @param theTransactionRepository The transaction repository, cannot be null.
   */
  @Autowired
  public TransactionService(
          final TransactionRepositoryImp theTransactionRepository) {
    transactionRepository = theTransactionRepository;
  }

  /**
   * Save a transaction
   *
   * @param id the transaction identifier, must be greater that zero.
   * @param type the type, cannot be null or empty.
   * @param amount the amount, cannot be zero.
   * @param parentId the parent id, might be null.
   */
  public void save(final Long id, final String type, final double amount, final Long parentId) {

    Assert.notNull(id, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());
    Assert.isTrue(id > 0, ErrorCode.TRANSACTION_ID_MUST_BE_GREATER_THAT_ZERO.getErrorMessage());
    Assert.hasLength(type, ErrorCode.TRANSACTION_TYPE_NULL_OR_EMPTY.getErrorMessage());
    Assert.isTrue(amount != 0, ErrorCode.AMOUNT_CANNOT_BE_ZERO.getErrorMessage());

    if (parentId != null && parentId != 0) {
      if (!transactionRepository.getTransactionById(parentId).isPresent()) {
        throw new ParentIdNotFoundException(ErrorCode.PARENT_ID_NOT_FOUND.getErrorMessage());
      }
    }
    transactionRepository.save(new Transaction(id, type, amount, parentId));
  }

  /**
   * Retrieves a list of transactions id by type.
   *
   * @param theType cannot be null or empty.
   */
  public List<Long> getTransactionIdsByType(final String theType) {
    Assert.hasLength(theType, ErrorCode.TRANSACTION_TYPE_NULL_OR_EMPTY.getErrorMessage());
    List<Transaction> result = transactionRepository.getTransactionsByType(theType);

    return result.stream().map(x -> x.getId()).collect(Collectors.toList());
  }

  /**
   * Return a long value that represent the sum of related transactions.
   *
   * @param theId cannot be null.
   * @return a double with the sum of related transactions.
   */
  public double getSumRelatedTransactions(final Long theId) {
    Assert.notNull(theId, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());

    Transaction transaction = transactionRepository.getTransactionById(theId)
            .orElseThrow(() -> new TransactionNotFoundException(ErrorCode.TRANSACTION_NOT_FOUND.getErrorMessage()));

    double result =
            transactionRepository.getTransactionsByParentId(theId).stream().collect(Collectors.summingDouble(x -> x.getAmount()));

    return transaction.getAmount() + result;
  }
}
