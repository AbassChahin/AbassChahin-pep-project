package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    // Constructor which creates new accountDAO
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    // Constructor if AccountDAO given
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // Add new account using accountDAO
    // Must contain these conditions: username is not blank, does not already exist, and pw is at least 4 characters long
    public Account addAccount(Account account) {
        if (account.getPassword().length() <= 3) {
            return null;
        } else if (account.getUsername() == null || account.getUsername().isEmpty()) {
            return null;
        } else if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        } else {
            return accountDAO.insertAccount(account);
        }
    }

    // Match account info to login
    public Account loginAccount(Account account) {
        Account loggedAccount = accountDAO.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (loggedAccount != null) {
            return loggedAccount;
        } else {
            return null;
        }
    }

    // Get account by id
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    // Get account by username
    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }
}
