package petclinic.pageobjects;

import org.apache.commons.lang3.StringUtils;

public class Owner {

    public Owner(){
        this.firstName= StringUtils.EMPTY;
        this.lastName=StringUtils.EMPTY;
        this.address=StringUtils.EMPTY;
        this.city=StringUtils.EMPTY;
        this.telephone=StringUtils.EMPTY;
    }

    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String telephone;

    public void setFirstName(final String firstname) {
        this.firstName = firstname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }
}
