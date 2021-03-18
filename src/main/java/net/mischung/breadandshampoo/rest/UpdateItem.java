package net.mischung.breadandshampoo.rest;

import javax.validation.constraints.NotBlank;

public class UpdateItem {

    @NotBlank
    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "UpdateItem{" +
                "item='" + item + '\'' +
                '}';
    }

}
