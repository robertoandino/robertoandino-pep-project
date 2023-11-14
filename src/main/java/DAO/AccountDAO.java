package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account newUser(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            String sql = "INSERT INTO Account (userName, userPassword)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            
            //ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            //if(pkeyResultSet.next()){
                //int generate_author_id = (int) pkeyResultSet.getLong(1);
                //return new Account(generate_author_id, account.getUsername());
                return new Account(account.getUsername(), account.getPassword());
            
        }catch(SQLException e){
                System.out.println(e.getMessage());
        }
        
        return null;
    }

}


