package net.mischung.breadandshampoo.rest;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class InsertItem {

    @NotBlank
    @Length(min = 1,max = 255)
    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "InsertItem{" +
                "item='" + item + '\'' +
                '}';
    }

}
