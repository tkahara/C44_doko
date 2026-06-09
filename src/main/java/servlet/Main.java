package servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.Account;
import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;

@WebServlet("/Main")
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024, // 1MB
  maxFileSize = 5 * 1024 * 1024,     // 5MB
  maxRequestSize = 10 * 1024 * 1024  // 10MB
)
public class Main extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  // GET: 一覧表示
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // つぶやきリストを取得してリクエストスコープに保存
    GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
    List<Mutter> mutterList = getMutterListLogic.execute();
    request.setAttribute("mutterList", mutterList);
    
    // セッションからログインユーザーを取得
    HttpSession session = request.getSession();
//    model.Login loginUser = (model.Login) session.getAttribute("loginUser");
    Account loginUser = (Account) session.getAttribute("loginUser");
    
    if (loginUser == null) {
      response.sendRedirect("index.jsp");
    } else {
      RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
      dispatcher.forward(request, response);
    }
  }
  
  // POST: つぶやき投稿処理（画像アップロード対応）
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String text = request.getParameter("text");
    
    // 入力値チェック
    if (text != null && text.length() != 0) {
      HttpSession session = request.getSession();
      Account loginUser = (Account) session.getAttribute("loginUser");
      
      // 画像データの取得
      Part imagePart = request.getPart("image");
      byte[] imageData = null;
      if (imagePart != null && imagePart.getSize() > 0) {
    	  System.out.print("aa");
        try (InputStream is = imagePart.getInputStream();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
        	  System.out.print("bb");
          byte[] data = new byte[1024];
          int nRead;
          while ((nRead = is.read(data, 0, data.length)) != -1) {
        	  System.out.print("cc");
            buffer.write(data, 0, nRead);
          }
          buffer.flush();
          imageData = buffer.toByteArray();
        }
      }
	  System.out.print(imageData);
      // つぶやきオブジェクトの作成（現在時刻を設定）
    //  Timestamp createdAt = new Timestamp(System.currentTimeMillis());
   //   Mutter mutter = new Mutter(loginUser.getUserId(), text, imageData);
      Mutter mutter = new Mutter(loginUser.getUserId(), text, imageData);

      
      // 投稿処理の実行
      PostMutterLogic postMutterLogic = new PostMutterLogic();
      postMutterLogic.execute(mutter);
    } else {
      request.setAttribute("errorMsg", "つぶやきが入力されていません");
    }
    
    // 最新のつぶやきリストを取得して画面表示
    GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
    List<Mutter> mutterList = getMutterListLogic.execute();
    request.setAttribute("mutterList", mutterList);
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
    dispatcher.forward(request, response);
  }
}
