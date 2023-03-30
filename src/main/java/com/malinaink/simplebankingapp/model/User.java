package com.malinaink.simplebankingapp.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private String firstName;
    private String lastName;
    private final String userName;
//Используем временно final как маркер, чтобы помнить, какие поля должны быть не изменяемыми
    private String passWord;

    private final Set<Account> accounts = new HashSet<>();
// пока делаем фиксированный Сет(уникальное множество), будто открывают все акки сразу


    public User(String firstName, String lastName, String userName, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
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

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

//    Перепишем геттер для Сета аккаунтов, чтоб нельзя было модифицировать аккаунты

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(this.accounts);
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
