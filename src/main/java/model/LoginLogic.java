package model;

import dao.AccountsDAO;

public class LoginLogic {
 // public boolean execute(Login login) {
	  public Account execute(Login login) {	  
//	public boolean execute(User user) {  
    AccountsDAO dao = new AccountsDAO();
    Account account = dao.findByLogin(login);
//    Account account = dao.findByLogin(user);
//    return account != null;
    return account;
  }
}