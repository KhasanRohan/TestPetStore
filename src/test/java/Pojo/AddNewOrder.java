package Pojo;

public class AddNewOrder {
    private Integer id;
    private Integer petId;

    private String status;
    private boolean complete;

    public AddNewOrder(Integer id, Integer petId, String status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.status = status;
        this.complete = complete;
    }

    public AddNewOrder() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getPetId() {
        return petId;
    }

    public String getStatus() {
        return status;
    }

    public boolean isComplete() {
        return complete;
    }
}
