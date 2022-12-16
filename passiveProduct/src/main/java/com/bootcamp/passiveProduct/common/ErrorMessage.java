package com.bootcamp.passiveProduct.common;

public enum ErrorMessage {
    CLIENT_NOT_FOUND("El cliente no existe"),
    PERSONAL_AHORRO_RESTRICTION("Ya tiene con otra cuenta de ahorro personal activa"),
    PERSONAL_FIJO_RESTRICTION("Ya tiene con otra cuenta de plazo fijo personal activa"),
    PERSONAL_CTE_RESTRICTION("Ya tiene con otra cuenta corriente personal activa"),
    EMPRESARIAL_ACCOUNT_RESTRICTION("No se permite tener una cuenta de este tipo a clientes empresariales."),
    ACCOUNTTYPE_NOT_FOUND("El tipo de cuenta indicado no existe"),
    OPENAMOUNT_RESTRICTION("El tipo de cuenta indicado no existe"),
    ACCOUNT_NOT_FOUND("La cuenta no existe"),
    LIMIT_ACCOUNT("Saldo insuficiente");

    private String value;
    ErrorMessage(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
