package com.mendel.mendelchallenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
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
  @JsonProperty("parent_id")
  private Long parentId;

}