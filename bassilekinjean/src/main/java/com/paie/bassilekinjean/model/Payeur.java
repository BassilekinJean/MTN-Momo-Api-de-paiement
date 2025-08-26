package com.paie.bassilekinjean.model;

import com.paie.bassilekinjean.enums.PayerIdType;

import lombok.Data;

@Data
public class Payeur {

    private String partyId;

    private PayerIdType partyIdType;

}
