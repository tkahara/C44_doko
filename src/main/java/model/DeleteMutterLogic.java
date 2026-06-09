package model;
import dao.MuttersDAO;
public class DeleteMutterLogic {
  public void execute(int id) {
    MuttersDAO dao = new MuttersDAO();
    dao.deleteMutterById(id);
  }
}

