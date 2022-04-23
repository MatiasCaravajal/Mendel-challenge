package com.mendel.mendelchallenge.domain;

import java.util.Locale;

public enum ErrorCode {

  TRANSACTION_ID_NULL("The transaction id cannot be null."),
  TRANSACTION_NULL("The transaction cannot be null."),
  TRANSACTION_TYPE_NULL("The transaction cannot be null."),
  PARENT_ID_NOT_FOUND("The parent id does not belong to a transaction.");

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

  /**
   * Retrieves the <code>Key</code> that represents <code>this
   * ErrorCode</code>. This key is the <code>ErrorCode.name()</code> as
   * lowercase snakecase.
   * @return A String representing this ErrorCode key, never null.
   */
  public String getKey() {
    return this.name().toLowerCase(Locale.ROOT);
  }
}