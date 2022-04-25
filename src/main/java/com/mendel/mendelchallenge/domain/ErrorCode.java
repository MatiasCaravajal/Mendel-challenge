package com.mendel.mendelchallenge.domain;

/**
 * The error codes to describe the error to unexpected behavior.
 */
public enum ErrorCode {

  TRANSACTION_ID_NULL("The transaction id cannot be null."),
  TRANSACTION_NULL("The transaction cannot be null."),
  TRANSACTION_NOT_FOUND("The transaction not found."),
  TRANSACTION_TYPE_NULL_OR_EMPTY("The transaction cannot be null or empty."),
  PARENT_ID_NOT_FOUND("The parent id does not belong to a transaction."),
  TRANSACTION_ALREADY_EXIST("The transaction already exists for the specified id."),
  TRANSACTION_ID_MUST_BE_GREATER_THAT_ZERO("the transaction id must be greater than zero."),
  AMOUNT_CANNOT_BE_ZERO("The amount cannot be zero.");

  /** The errorMessage explaining this code, never null. **/
  private String errorMessage;

  /**
   * Constructor that establishes the contract of this <code>Enum</code> to
   * create an <code>ErrorCode</code> with a errorMessage that explains what
   * represents this key.
   * @param message A String containing a descriptive errorMessage, never null.
   */
  ErrorCode(final String message) {
    this.errorMessage = message;
  }

  /**
   * Retrieves a descriptive <code>errorMessage</code> associated to this
   * <code>ErrorCode</code>.
   * @return The errorMessage represented by this <code>ErrorCode</code>,
   * never null.
   */
  public String getErrorMessage() {
    return errorMessage;
  }

}
