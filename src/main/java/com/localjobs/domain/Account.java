package com.localjobs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNTS")
public class Account extends AbstractEntity {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SKILLS", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<String>();

    public Account() {
        super();
    }

    public Account(String username, String password, String firstName, String lastName, String address,
            List<String> skills) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.skills = skills;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Account [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
                + lastName + ", address=" + address + "]";
    }
    
}
