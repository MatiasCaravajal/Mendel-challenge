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
     * The amount cannot be negative.
     */
    private double amount;
    /**
     *  The transaction parent id, might be null
     */
    private Long parent_id;
    /**
     * The constructor with mandatory parameters.
     *
     * @param theId the transaction identifier, cannot be null.
     * @param theType the type, cannot be null or empty.
     * @param theAmount the amount can not be negative.
     * @param theParent_id the parent id, might be null.
     */
    public Transaction(final Long theId,
                       final String theType,
                       final double theAmount,
                       final Long theParent_id) {

        Assert.notNull(theId, "the transaction id cannot be null.");
        Assert.hasLength(theType, "the type cannot be null or empty.");
        Assert.isTrue(amount >= 0, "The amount cannot be negative.");

        id = theId;
        type = theType;
        amount = theAmount;
        parent_id = theParent_id;
    }
}
