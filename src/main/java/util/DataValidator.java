package util;

import model.Message;
import org.json.JSONArray;
import org.json.JSONObject;

public class DataValidator {
    
    private static void pessoaIsValid(JSONArray error, String json){
        error.put(ParseHelper.testOnlyNumber(json, "id", -1).toJson());
        error.put(ParseHelper.testIfIsCPF(json, "cpf").toJson());
        error.put(ParseHelper.testNotEmpty(json, "nome", 100).toJson());
        error.put(ParseHelper.testIfIsEmail(json, "email").toJson());
        error.put(ParseHelper.testIfIsDate(json, "dtNascimento").toJson());
        
        JSONObject obj = new JSONObject(json);
        
        if(obj.has("telefone")){
            error.put(ParseHelper.testOnlyNumber(obj.get("telefone").toString(), "ddd", -1).toJson());
            error.put(ParseHelper.testOnlyNumber(obj.get("telefone").toString(), "numero", -1).toJson());
        }else{
            error.put(new Message("Erro de Telefone", "O campo Telefone não foi encontrado!", 
                            "finderror", "telefone").toJson());
        }
        if(obj.has("endereco")){
            error.put(ParseHelper.testOnlyNumber(obj.get("endereco").toString(), "numCasa", -1).toJson());
            error.put(ParseHelper.testNotEmpty(obj.get("endereco").toString(), "bairro", -1).toJson());
            error.put(ParseHelper.testNotEmpty(obj.get("endereco").toString(), "cidade", -1).toJson());
            error.put(ParseHelper.testNotEmpty(obj.get("endereco").toString(), "estado", -1).toJson());
            error.put(ParseHelper.testNotEmpty(obj.get("endereco").toString(), "rua", -1).toJson());
        }else{
            error.put(new Message("Erro de Endereço", "O campo Endereço não foi encontrado!", 
                            "finderror", "endereco").toJson());
        }
    }
    
    public static String adminIsValid(String json){
        JSONArray error = new JSONArray();
        
        pessoaIsValid(error, json);
        error.put(ParseHelper.testNotEmptyAlphabetic(json, "cargo", -1).toJson());
        error.put(ParseHelper.testIfIsDate(json, "dtAdmissao").toJson());
        
        return error.toString();
    }
    
    public static String clienteIsValid(String json){
        JSONArray error = new JSONArray();
        
        pessoaIsValid(error, json);
        error.put(ParseHelper.testIfIsDate(json, "dtCadastro").toJson());
        
        return error.toString();
    }
    
    public static String usuarioIsValid(String json){
        JSONArray error = new JSONArray();
        
        error.put(ParseHelper.testOnlyNumber(json, "idUser", -1).toJson());
        error.put(ParseHelper.testIfIsCPF(json, "cpf").toJson());
        error.put(ParseHelper.testNotEmpty(json, "senha", -1).toJson());
        error.put(ParseHelper.testOnlyNumber(json, "tipo", -1).toJson());
        error.put(ParseHelper.testIfIsBoolean(json, "active").toJson());
        
        return error.toString();
    }
    
    private static void checkConta(JSONArray error, String json){
        error.put(ParseHelper.testOnlyNumber(json, "agencia", -1).toJson());
        error.put(ParseHelper.testOnlyNumber(json, "numeroConta", -1).toJson());
        error.put(ParseHelper.testIfIsCPF(json, "cpfTitular").toJson());
        error.put(ParseHelper.testIfIsDouble(json, "saldo").toJson());
        error.put(ParseHelper.testOnlyNumber(json, "idConta", -1).toJson());
        error.put(ParseHelper.testIfIsDate(json, "dtCreation").toJson());
    }
    
    public static String contaIsValid(String json){
        JSONArray error = new JSONArray();
        
        checkConta(error, json);
        
        return error.toString();
    }
    
    public static String transacaoIsValid(String json){
        JSONArray error = new JSONArray();
        
        JSONObject obj = new JSONObject(json);
        JSONObject contas = new JSONObject();
        JSONArray aux = new JSONArray();
        
        //Erros da conta origem
        checkConta(aux, obj.get("from").toString());
        contas.put("from", aux);
        
        //Erros da conta destino
        aux = new JSONArray();
        checkConta(aux, obj.get("to").toString());
        contas.put("to", aux);
        
        //Passando erros para um objeto a ser inserido no array de erros principal
        JSONObject objForArray = new JSONObject();
        objForArray.put("contas", contas);
        
        //Inserindo erros no JSON principal
        error.put(objForArray);
        error.put(ParseHelper.testOnlyNumber(json, "tipo", -1).toJson());
        error.put(ParseHelper.testIfIsDouble(json, "valMovimentado").toJson());
        error.put(ParseHelper.testOnlyNumber(json, "idTransacao", -1).toJson());
        error.put(ParseHelper.testIfIsDate(json, "dtMovimento").toJson());
        
        return error.toString();
    }
    
    public static String removeNotErrorEntrys(String json){
        JSONArray arr = new JSONArray(json);
        JSONArray newArr = new JSONArray();
        
        arr.forEach(e -> {
        
            JSONObject o = new JSONObject(e.toString());
            
            if(!o.getString("type").equals("noterror")){
                newArr.put(o);
            }
        
        });
        
        return newArr.toString();
    }
    
    public static boolean containsMessageErrors(String json){
        JSONArray arr = new JSONArray(json);
        
        for(Object e : arr){
            JSONObject o = new JSONObject(e.toString());
            if(o.getString("type").equals("error") || o.getString("type").equals("nullerror") || 
                    o.getString("type").equals("finderror")){
                return true;
            }
        }
        
        return false;
    }
    
}
