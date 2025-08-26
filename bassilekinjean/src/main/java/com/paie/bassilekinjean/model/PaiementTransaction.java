package com.paie.bassilekinjean.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PaiementTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String amount;

    private String currency;

    private String status;

    private String externalId;

    private Payeur payeur;

    private String payerMessage;

    private String payeeNote;

}
