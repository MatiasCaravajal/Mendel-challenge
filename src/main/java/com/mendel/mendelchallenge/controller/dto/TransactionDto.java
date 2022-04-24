package com.mendel.mendelchallenge.controller.dto;

import com.mendel.mendelchallenge.domain.Transaction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/**
 *  Data Transfer Object used to retrieve information about an transaction from
 *  api.
 */
public class TransactionDto {
    /**
     * The transaction id, cannot be null.
     */
    private Long id;
    /**
     * The transaction type, cannot be null or empty.
     */
    private String type;
    /**
     * The amount cannot be zero.
     */
    private double amount;
    /**
     * The transaction parent id, might be null
     */
    private Long parentId;

  /**
   * Create a Transaction entity.
   *
   * @return a Transaction. Cannot be null.
   */
  public Transaction create() {
        return new Transaction(id, type, amount, parentId);
    }

}