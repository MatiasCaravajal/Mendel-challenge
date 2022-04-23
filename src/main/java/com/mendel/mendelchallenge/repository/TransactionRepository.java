package com.mendel.mendelchallenge.repository;

import java.util.Optional;

import com.mendel.mendelchallenge.domain.Transaction;

public interface TransactionRepository {
    /**
     * Save transaction.
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
}
