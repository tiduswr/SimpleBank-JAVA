package util;

import model.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

public class ParseHelper {
    public static Message testOnlyNumber(String json, String field, int maxLen){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(maxLen != -1){
                    if(!(test.length() <= maxLen)){
                        return new Message("Tamanho muito grande", 
                            "O Campo " + field + " supera o maximo de " + String.valueOf(maxLen) + " caracteres!", 
                                "error", field);
                    }
                }
                if(!RegexValidation.TestString(RegexValidation.StandardRegexString.ONLY_NUMBERS, test)){
                    return new Message("Erro de " + field, "O campo " + field + " não esta em formato numérico!", 
                            "error", field);
                }else{
                    return new Message("Nenhum erro", 
                                "O Campo " + field + " não contem erros!", "noterror", field);
                }
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    
    public static Message testOnlyNumber(String json, String field, int maxLen, String ignore){
        JSONObject j = new JSONObject(json);
        String aux = j.getString(field);
        
        for(int i = 0; i < ignore.length(); i++){
            String c = ignore.substring(i,i+1);
            aux = aux.replace(c, "");
        }
        
        j.remove(field);
        j.put(field, aux);
        
        return testOnlyNumber(j.toString(), field, maxLen);
    }
    
    public static Message testNotEmptyAlphabetic(String json, String field, int maxLen){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(maxLen != -1){
                    if(!(test.length() <= maxLen)){
                        return new Message("Tamanho muito grande", 
                            "O Campo " + field + " supera o maximo de " + String.valueOf(maxLen) + " caracteres!", 
                                "error", field);
                    }
                }
                if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, test)){
                    return new Message("Erro de " + field, "O campo " + field + " não esta escrito com letras entre A-Z!", 
                            "error", field);
                }else{
                    return new Message("Nenhum erro", 
                                "O Campo " + field + " não contem erros!", "noterror", field);
                }
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    public static Message testNotEmpty(String json, String field, int maxLen){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(maxLen != -1){
                    if(!(test.length() <= maxLen)){
                        return new Message("Tamanho muito grande", 
                            "O Campo " + field + " supera o maximo de " + String.valueOf(maxLen) + " caracteres!", 
                                "error", field);
                    }
                }
                if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY, test)){
                    return new Message("Erro de " + field, "O campo " + field + " esta vazio!", 
                            "error", field);
                }else{
                    return new Message("Nenhum erro", 
                                "O Campo " + field + " não contem erros!", "noterror", field);
                }
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    private static boolean testDate(String value){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
           String aux = value;
           aux = aux.replaceAll("/", "");
           if(RegexValidation.TestString(RegexValidation.StandardRegexString.ONLY_NUMBERS, aux)){
               sdf.parse(value);
               return true;
           }else{
               return false;
           }
        }catch(ParseException ex){
            return false;
        }
    }
    public static Message testIfIsDate(String json, String field){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(!testDate(test)){
                    return new Message("Erro de " + field, "O campo " + field + " não é uma Data!", 
                            "error", field);
                }
                return new Message("Nenhum erro", 
                        "O Campo " + field + " não contem erros!", "noterror", field);
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    private static boolean testBoolean(String value){
        if(value != null){
            return value.equals("1") || value.equals("0") || value.equals("true") || value.equals("false");
        }
        return false;
    }
    public static Message testIfIsBoolean(String json, String field){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(!testBoolean(test)){
                    return new Message("Erro de " + field, "O campo " + field + " não é um boolean!", 
                            "error", field);
                }
                return new Message("Nenhum erro", 
                        "O Campo " + field + " não contem erros!", "noterror", field);
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    public static Message testIfIsCPF(String json, String field){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(!RegexValidation.TestString(RegexValidation.StandardRegexString.CPF, test)){
                    return new Message("Erro de " + field, "O campo " + field + " não é um CPF válido!", 
                            "error", field);
                }
                return new Message("Nenhum erro", 
                        "O Campo " + field + " não contem erros!", "noterror", field);
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    public static Message testIfIsFone(String json, String field){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(!RegexValidation.TestString(RegexValidation.StandardRegexString.FONE, test)){
                    return new Message("Erro de " + field, "O campo " + field + " não é um Telefone válido!", 
                            "error", field);
                }
                return new Message("Nenhum erro", 
                        "O Campo " + field + " não contem erros!", "noterror", field);
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    public static Message testIfIsDouble(String json, String field){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString().replace(".", ",");
            
            if(test != null){
                if(!RegexValidation.TestString(RegexValidation.StandardRegexString.DECIMAL_VALUE, test)){
                    return new Message("Erro de " + field, "O campo " + field + " não é um decimal com virgula válido!", 
                            "error", field);
                }
                return new Message("Nenhum erro", 
                        "O Campo " + field + " não contem erros!", "noterror", field);
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    public static Message testIfIsEmail(String json, String field){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            String test = j.get(field).toString();
            
            if(test != null){
                if(!RegexValidation.TestString(RegexValidation.StandardRegexString.EMAIL, test)){
                    return new Message("Erro de " + field, "O campo " + field + " não é um E-mail válido!", 
                            "error", field);
                }
                return new Message("Nenhum erro", 
                        "O Campo " + field + " não contem erros!", "noterror", field);
            }else{
                return new Message("Valor Nulo", 
                    "O Campo " + field + " é nulo!", "nullerror", field);
            }
            
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
    public static Message fieldExist(String json, String field, int maxLen){
        JSONObject j = new JSONObject(json);
        
        if(j.has(field)){
            if(maxLen != -1){
                if(!(j.get(field).toString().length() <= maxLen)){
                        return new Message("Tamanho muito grande", 
                            "O Campo " + field + " supera o maximo de " + String.valueOf(maxLen) + " caracteres!", 
                                "error", field);
                    }
                }
                return new Message("Nenhum erro", 
                            "O Campo " + field + " não contem erros!", "noterror", field);
        }else{
            return new Message("Campo não Encontrado", 
                    "O Campo " + field + " não foi encontrado!", "finderror", field);
        }
    }
}
