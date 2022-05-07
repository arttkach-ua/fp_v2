package model.entity;

import java.io.Serializable;

public class Entity implements Serializable {
    long ID;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
