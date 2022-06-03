package com.epam.tkach.carrent.model.entity.enums;

public enum OrderStatuses {
    NEW(1),
    APPROVED(2),
    CANCELED(3),
    INPROGRESS(4),
    HASDAMAGE(5),
    COMPLETE(6);


    private int value;

    private OrderStatuses(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
