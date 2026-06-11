//ますだ参上仕り候

package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Account;
import model.LoginLogic;

@WebServlet("/Login")
public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // リクエストパラメータの取得
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    String pass = request.getParameter("pass");
    // Userインスタンス（ユーザー情報）の生成
 //   User user = new User(name, pass);
//    Login login = new Login("minato", "1234");
//    model.Login login  = new model.Login(name, pass);
    model.Login login  = new model.Login(name, pass);

    // ログイン処理
    LoginLogic loginLogic = new LoginLogic();
 //   b     boolean isLogin = loginLogic.execute(login);oolean isLogin = loginLogic.execute(user);
 //     boolean isLogin = loginLogic.execute(login);
 //     boolean isLogin = loginLogic.execute(login);
    Account	accouts = loginLogic.execute(login); 

    // ログイン成功時の処理
  //  if (isLogin) {
    if (accouts != null) { 	
      // ユーザー情報をセッションスコープに保存
      HttpSession session = request.getSession();
 //     session.setAttribute("loginUser", user);
 //     session.setAttribute("loginUser", login);
      session.setAttribute("loginUser", accouts);
    }
    // ログイン結果画面にフォワード
    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/loginResult.jsp");
    dispatcher.forward(request, response);
  }
}