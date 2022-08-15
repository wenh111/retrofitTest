package com.org.retrofittest;


public class UserResults {

    public UserResults(String name, String account, String password, String telephone_number) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.telephone_number = telephone_number;
    }
    int id;
    String name;
    String account;
    String password;
    String telephone_number;

    @Override
    public String toString() {
        return "UserResults{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", telephone_number='" + telephone_number + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }
}
