package net.mischung.breadandshampoo.model;

public class ListManagementException extends RuntimeException {

    private Integer itemId;

    public ListManagementException(String message) {
        super(message);
    }

    public ListManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListManagementException(Throwable cause) {
        super(cause);
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}
