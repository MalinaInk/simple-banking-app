package com.malinaink.simplebankingapp.service;

import com.malinaink.simplebankingapp.Exception.InvalidPasswordException;
import com.malinaink.simplebankingapp.Exception.UserAlreadyExistsException;
import com.malinaink.simplebankingapp.Exception.UserNotFoundException;
import com.malinaink.simplebankingapp.model.Account;
import com.malinaink.simplebankingapp.model.Currency;
import com.malinaink.simplebankingapp.model.User;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.*;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public User addUser(User user) {
        if (users.containsKey(user.getUserName())) {
            throw new UserAlreadyExistsException();
        }
        users.put(user.getUserName(), user);
        return createNewUserAccount(user);
    }

    //Всего юзера не передаем, только логин и неизменяемые поля.
    public User updateUser(String userName, String firstName, String lastName) {
        if (!users.containsKey(userName)) {
            throw new UserNotFoundException();
        }
        User user = users.get(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public void updatePassword(String userName, String password, String newPassword) {
        if (!users.containsKey(userName)) {
            throw new UserNotFoundException();
        }
        User user = users.get(userName);
        if (!user.getPassWord().equals(password)) {
            throw new InvalidPasswordException();
        }
        user.setPassWord(newPassword);
    }

    public User removeUser(String userName) {
        if (!users.containsKey(userName)) {
            throw new UserNotFoundException();
        }
        return users.remove(userName);
    }

    public User getUser(String userName) {
        if (!users.containsKey(userName)) {
            throw new UserNotFoundException();
        }
        return users.get(userName);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }
//    values возвращается интерфейсом Collection, поэтому мы мигрировали тип

    private User createNewUserAccount(User user) {
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.RUB));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.EUR));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.USD));
    return user;
    }
}
