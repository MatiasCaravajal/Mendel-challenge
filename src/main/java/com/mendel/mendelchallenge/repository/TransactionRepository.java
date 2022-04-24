package com.mendel.mendelchallenge.repository;

import java.util.List;
import java.util.Optional;
import com.mendel.mendelchallenge.domain.Transaction;

public interface TransactionRepository {
    /**
     * Save a transaction.
     *
     * @param theTransaction cannot be null.
     */
     void save(Transaction theTransaction);

    /**
     * Retrieves the transaction by id.
     *
     * @param theId the id, cannot be null
     *
     * @return An optional containing the found transaction or an empty
     * value.
     */
    Optional<Transaction> getTransactionById(Long theId);

    /**
     * Retrieves a list with related transactions.
     *
     * @param theId the id cannot be null.
     *
     * @return a list with the related transactions. Cannot be null, but can
     * be empty.
     */
    List<Transaction> getTransactionsByParentId(Long theId);

    /**
     * Retrieves a transactions by type
     *
     * @param theType the type cannot be null or empty.
     *
     * @return a list with the transactions. Cannot be null, but can
     * be empty.
     */
    List<Transaction> getTransactionsByType(String theType);
}
