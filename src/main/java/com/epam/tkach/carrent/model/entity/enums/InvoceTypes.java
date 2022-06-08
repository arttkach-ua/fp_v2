package com.epam.tkach.carrent.model.entity.enums;

public enum InvoceTypes {
    RENT(1),
    DAMAGE(2);

    private int value;

    InvoceTypes(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public static InvoceTypes getByID(int id){
        switch (id){
            case 1: return RENT;
            case 2: return DAMAGE;
            default: return null;
        }
    }
}
