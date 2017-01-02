package com.mycompany.myapp.domain.enums;

/**
 * Created by Vladimir on 21.12.2016.
 */
public enum StatusShop {
    ACTIVE, INACTIVE;
    public static StatusShop invertStatus(StatusShop status){
        if (status == ACTIVE) return INACTIVE;
        return ACTIVE;
    }
}
