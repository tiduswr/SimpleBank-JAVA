
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Administrador;
import org.json.JSONArray;
import org.json.JSONObject;

public class main {

    public static void main(String args[]) {
        JSONObject obj = new JSONObject();
        
        obj.put("id", 1);
        obj.put("cpf", "126.796.924-54a");
        obj.put("nome", "Harllem");
        obj.put("email", "harllem@gmail.com");
        obj.put("dtNascimento", "13/04/1998");
        obj.put("cargo", "Gerente");
        obj.put("dtAdmissao", "13/12/2004a");
        
        JSONObject end = new JSONObject();
        
        end.put("numCasa", 182);
        end.put("bairro", "Novo Horizonte");
        end.put("cidade", "PATOS");
        end.put("estado", "PB");
        end.put("rua", "Manoel Mauricio de Oliveira");

        JSONObject tel = new JSONObject();
        
        tel.put("ddd", 83);
        tel.put("numero", "981909882");
        
        obj.put("endereco", end);
        obj.put("telefone", tel);
        
        JSONArray arr = new JSONArray(util.DataValidator.adminIsValid(obj.toString()));
        arr = new JSONArray(util.DataValidator.removeNotErrorEntrys(arr.toString()));
        
        System.out.println("\n");
        
        arr.forEach(e -> {
        
            System.out.println("-> " + e);
        
        });
        
        try {
            Administrador adm = new Administrador(obj.toString());
            System.out.println(adm);
        } catch (ParseException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
