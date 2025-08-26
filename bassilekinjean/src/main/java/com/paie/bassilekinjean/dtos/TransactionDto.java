package com.paie.bassilekinjean.dtos;

import com.paie.bassilekinjean.model.Payeur;

public record TransactionDto(
    String amount,
    String currency,
    String externalId,
    Payeur payer,
    String payerMessage,
    String payeeNote
    
) {

}
