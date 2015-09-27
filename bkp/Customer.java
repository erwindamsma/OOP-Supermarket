package supermarket;

public class Customer extends Person{
    private final CustomerGroup customerGroup;

    public Customer(CustomerGroup customerGroup, String firstName, String lastName){
        super(firstName, lastName);
        this.customerGroup = customerGroup;
    }
}