package net.mischung.breadandshampoo.rest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UpdateItem {

    @NotNull
    @Positive
    private Integer itemId;

    @NotBlank
    private String item;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "UpdateItem{" +
                "itemId=" + itemId +
                ", item='" + item + '\'' +
                '}';
    }

}
