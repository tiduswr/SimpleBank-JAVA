package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimpleDateStruct {
    private int dia, mes, ano;
    private Date date;
    
    public SimpleDateStruct(Date date) {
        config(date);
    }
    
    public SimpleDateStruct(String date) throws ParseException{
        Date d;
        
        SimpleDateFormat f = new SimpleDateFormat();
        d = f.parse(date);
        
        config(d);
    }
    
    private void config(Date date){
        GregorianCalendar dayInfo = new GregorianCalendar();
        dayInfo.setTime(date);
        
        this.ano = dayInfo.get(Calendar.YEAR);
        this.mes = dayInfo.get(Calendar.MONTH);
        this.dia = dayInfo.get(Calendar.DATE);
        this.date = date;
    }
    
    public int getDia() {
        return dia;
    }
    public int getMes() {
        return mes;
    }
    public int getAno() {
        return ano;
    }
    public Date getDate(){
        return date;
    }
}
