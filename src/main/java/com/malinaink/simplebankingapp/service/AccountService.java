package com.malinaink.simplebankingapp.service;

import com.malinaink.simplebankingapp.Exception.AccountNotFoundException;
import com.malinaink.simplebankingapp.Exception.InsufficientFundsException;
import com.malinaink.simplebankingapp.Exception.InvalidChangeAmountException;
import com.malinaink.simplebankingapp.Exception.UserNotFoundException;
import com.malinaink.simplebankingapp.model.Account;
import com.malinaink.simplebankingapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public Account changeBalance(String userName,
                                 String accountNumber,
                                 Operation operation,
                                 double amount) {
        //0. Проверка на допустимость суммы(положительное число и не ноль. Ноль в логах выглядит странно)
        if (amount <= 0) {
            throw new InvalidChangeAmountException();
        }
        //1. Нужно получить пользователя (если не нашли - непроверяемое исключение(саплаер в аргументе))
        User user = userService.getUser(userName);

        //2. Нужно получить список аккаунтов пользователя и найти нужный нам
        Account account =
                user.getAccounts().stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
        if (operation.equals(Operation.DEPOSIT)) {
            return depositOnAccount(account, amount);
        } else {
            return withdrawFromAccount(account, amount);
        }
    }

    private Account withdrawFromAccount(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        account.setBalance(account.getBalance() - amount);
        return  account;
    }

    private Account depositOnAccount(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        return account;
    }
}
