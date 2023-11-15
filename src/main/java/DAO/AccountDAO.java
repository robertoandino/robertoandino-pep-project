package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    /**
     * Retrieve all user accounts
     * @return all Accounts.
     */
    public List<Account> getAllAccounts(){

        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return accounts;

    }
    
    /**
     * //create new user account
     *
     */
    public Account insertAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            
            if(rowsAffected > 0){
                
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generatedAccountId = pkeyResultSet.getInt(1);
                    return new Account(generatedAccountId, account.getUsername(), account.getPassword());
                    //return new Account(account.getUsername(), account.getPassword());
                }
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
        }
        
        return null;
    }

}


