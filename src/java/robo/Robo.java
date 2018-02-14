package robo;

import chatterbot.Chatterbot;
import java.util.logging.Level;
import java.util.logging.Logger;
import processamentomorfologico.ProcessaHtmlVISL;

public class Robo {

    private Chatterbot c;
    private String hist;

    public Robo() {
        this.c = new Chatterbot("ChatterEdu");
        this.hist = "";
    }

    public void processarTexto(String txtTextoEntrada) {
        ProcessaHtmlVISL processamento = new ProcessaHtmlVISL();
        boolean processou = false;

        try {
            processou = processamento.processaTexto(txtTextoEntrada.replace("\n", "").replace("\r", ""));
        } catch (Exception ex) {
            //Logger.getLogger(Tomara.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (processou) {
            c = new Chatterbot("ChatterEdu");
            //txtHistoricoConversa.setText("");
            //panelPrincipal.setSelectedIndex(1);
        }
    }

    public void enviarMensagem(String txtHistoricoConversa, String txtMensagemUsuario) {
        if (c == null) {
            c = new Chatterbot("ChatterEdu");
        }
        
        String resposta = c.getResposta(txtMensagemUsuario);
        
        this.hist = txtHistoricoConversa;
        this.hist += "VISITANTE:  ";
        this.hist += txtMensagemUsuario.trim() + "\n";
        this.hist += "ChatterEdu: ";
        this.hist += resposta + "\n\n\n";

        txtMensagemUsuario = "";

        /*String resposta = c.getResposta(txtMensagemUsuario);
        String historico = "";

        historico += txtHistoricoConversa;
        historico += "VISITANTE:  ";
        historico += txtMensagemUsuario.trim() + "\n";
        historico += "ChatterEdu: ";
        historico += resposta + "\n\n\n";

        txtHistoricoConversa = historico;
        txtMensagemUsuario = "";
        hist = historico;*/
    }
    
    public String getHist(){
        return this.hist;
    }
    
    public void setHist(String hist){
        this.hist = hist;
    }

}
