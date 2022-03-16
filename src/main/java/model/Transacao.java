package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class Transacao implements JSONTransform {
    private long idTransacao;
    private Conta from, to;
    private TipoTransacao tipo;
    private double valMovimentado;
    private Date dtMovimento;

    public Transacao(long idTransacao, Conta from, Conta to, TipoTransacao tipo, double valMovimentado, Date dtMovimento) {
        this.idTransacao = idTransacao;
        this.from = from;
        this.to = to;
        this.tipo = tipo;
        this.valMovimentado = valMovimentado;
        this.dtMovimento = dtMovimento;
    }

    public Transacao() {}
    
    public Transacao(String json) throws ParseException {
    
        JSONObject j = new JSONObject(json);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        this.from = new Conta(j.get("from").toString());
        this.to = new Conta(j.get("to").toString());
        this.tipo = TipoTransacao.getByInt(j.getInt("tipo"));
        this.valMovimentado = j.getDouble("valMovimentado");
        this.dtMovimento = sdf.parse(j.getString("dtMovimento"));
        this.idTransacao = j.getLong("idTransacao");
    }
    
    public Conta getFrom() {
        return from;
    }
    public void setFrom(Conta from) {
        this.from = from;
    }
    public Conta getTo() {
        return to;
    }
    public void setTo(Conta to) {
        this.to = to;
    }
    public TipoTransacao getTipo() {
        return tipo;
    }
    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }
    public double getValMovimentado() {
        return valMovimentado;
    }
    public void setValMovimentado(double valMovimentado) {
        this.valMovimentado = valMovimentado;
    }
    public Date getDtMovimento() {
        return dtMovimento;
    }
    public void setDtMovimento(Date dtMovimento) {
        this.dtMovimento = dtMovimento;
    }
    public long getIdTransacao() {
        return idTransacao;
    }
    public void setIdTransacao(long idTransacao) {
        this.idTransacao = idTransacao;
    }
    
    @Override
    public String toString() {
        return "Transacao{" + "from=" + from + ", to=" + to + ", tipo=" + tipo + ", valMovimentado=" + valMovimentado + 
                ", dtMovimento=" + dtMovimento + '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("from", getFrom().toJson());
        json.put("to", getTo().toJson());
        json.put("tipo", getTipo().getValue());
        json.put("valMovimentado", getValMovimentado());
        json.put("idTransacao", getIdTransacao());
        json.put("dtMovimento", new SimpleDateFormat("dd/MM/yyyy").format(getDtMovimento()));
        
        return json;
    }
    
    public enum TipoTransacao implements JSONTransform{
        SAQUE(0){

            @Override
            public String toString(){
              return "Saque";
            }

            @Override
            public JSONObject toJson() {
                JSONObject json = new JSONObject();

                json.put("code", getValue());
                json.put("nome", toString());

                return json;
            }

        },
        DEPOSITO(1){

            @Override
            public String toString(){
              return "Depósito";
            }

            @Override
            public JSONObject toJson() {
                JSONObject json = new JSONObject();

                json.put("code", getValue());
                json.put("nome", toString());

                return json;
            }

        },
        TRANSFERENCIA(2){

            @Override
            public String toString(){
              return "Transferencia";
            }

            @Override
            public JSONObject toJson() {
                JSONObject json = new JSONObject();

                json.put("code", getValue());
                json.put("nome", toString());

                return json;
            }

        };

        private final int value;

        private TipoTransacao(int i){
            this.value = i;
        }

        public int getValue(){
            return this.value;
        }

        public static TipoTransacao getByInt(int i){
            switch(i){
                case 0:
                    return TipoTransacao.SAQUE;
                case 1:
                    return TipoTransacao.TRANSFERENCIA;
                case 2:
                    return TipoTransacao.DEPOSITO;
                default:
                    return null;
            }
        }

        public static int getQtdTipos(){
            return 3;
        }

    }
}
