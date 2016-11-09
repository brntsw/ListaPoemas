package poemas.exemplo.com.listapoemas.model;

/**
 * Created by BPardini on 09/11/2016.
 */

public class JsonReturn {

    public static final int RETORNO_SUCESSO = 200;
    public static final int RETORNO_SUCESSO_SEM_RESPOSTA = 204;
    public static final int ERRO = 500;
    public static final int NAO_ENCONTRADO = 404;
    public static final int NAO_DISPONIVEL = 503;

    private int returnStatus;
    private String resultString;

    public JsonReturn(int returnStatus, String resultString) {
        this.returnStatus = returnStatus;
        this.resultString = resultString;
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

}
