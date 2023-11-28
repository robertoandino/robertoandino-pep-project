package Service;

import Model.Account;
import DAO.AccountDAO;

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
     * gets account by username
     * @param username
     * @return
     */
    public Account getAccountByUsername(String username){
        return accountDAO.getAccountByUsername(username);
    }

    /**
     * inserts new account
     * @param account
     * @return inserted account or null
     */
    public Account newAccount(Account account){
        if(!accountExists(account.getUsername())){
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    /**
     * Checks if account exist
     * @param username
     * @return null or not null
     */    
    public boolean accountExists(String username) {
        
        return accountDAO.getAccountByUsername(username) != null;
    }
}
