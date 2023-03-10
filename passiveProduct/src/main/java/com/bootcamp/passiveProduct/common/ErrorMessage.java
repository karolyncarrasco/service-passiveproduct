package com.bootcamp.passiveProduct.common;

public enum ErrorMessage {
    CLIENT_NOT_FOUND("El cliente no existe"),
    PERSONAL_AHORRO_RESTRICTION("Ya tiene con otra cuenta de ahorro personal activa"),
    PERSONAL_FIJO_RESTRICTION("Ya tiene con otra cuenta de plazo fijo personal activa"),
    PERSONAL_CTE_RESTRICTION("Ya tiene con otra cuenta corriente personal activa"),
    EMPRESARIAL_ACCOUNT_RESTRICTION("No se permite tener una cuenta de este tipo a clientes empresariales."),
    ACCOUNTTYPE_NOT_FOUND("El tipo de cuenta indicado no existe"),
    OPENAMOUNT_RESTRICTION("Debe indicar el monto de apertura de la cuenta."),
    ACCOUNT_NOT_FOUND("La cuenta no existe"),
    LIMIT_ACCOUNT("Saldo insuficiente"),
    VIP_RESTRICTION("Para crear una cuenta VIP debe tener una TC"),
    PYME_RESTRICTION("Para crear una cuenta PYME debe tener una TC"),
    DEBIT_CARD_RESTRICTION("El tipo de cuenta no permite tener TD"),
    DEBIT_CARD_NOT_FOUND("La TD no existe"),
    DEBIT_CARD_ACCOUNT("La cuenta ya está relacionada");

    private String value;
    ErrorMessage(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
