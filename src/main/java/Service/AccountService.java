package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * constructor for creating new authorservice with accountDAO
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * constructor for mock behavior
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    /**
     * Retrieve all accounts
     * @return all accounts
     */
    public List<Account> getAllAccounts(){

        List<Account> accounts = accountDAO.getAllAccounts();
        
        return accounts;
    }

    /**
     * Add new account
     * 
     * @param account account object
     * @return persisted account if successful
     */
    public Account addAccount(Account account){

        Account test = accountDAO.insertAccount(account);

        return test;
    }
    
}
