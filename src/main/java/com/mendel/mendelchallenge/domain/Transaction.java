package com.mendel.mendelchallenge.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
@Builder
/**
 * The transaction entity.
 */
public class Transaction {
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
     *  The transaction parent id, might be null
     */
    private Long parentId;
    /**
     * The constructor with mandatory parameters.
     *
     * @param theId the transaction identifier, must be greater that zero.
     * @param theType the type, cannot be null or empty.
     * @param theAmount the amount, cannot be zero.
     * @param theParentId the parent id, might be null.
     */
    public Transaction(final Long theId,
                       final String theType,
                       final double theAmount,
                       final Long theParentId) {
        Assert.notNull(theId, ErrorCode.TRANSACTION_ID_NULL.getErrorMessage());
        Assert.isTrue(theId > 0, ErrorCode.TRANSACTION_ID_MUST_BE_GREATER_THAT_ZERO.getErrorMessage());
        Assert.hasLength(theType, ErrorCode.TRANSACTION_TYPE_NULL_OR_EMPTY.getErrorMessage());
        Assert.isTrue(theAmount != 0, ErrorCode.AMOUNT_CANNOT_BE_ZERO.getErrorMessage());

        id = theId;
        type = theType;
        amount = theAmount;
        parentId = theParentId;
    }
}
