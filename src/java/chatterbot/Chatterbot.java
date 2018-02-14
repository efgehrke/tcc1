package chatterbot;

import org.alicebot.ab.*;

public class Chatterbot {

    private Chat chat;
    private Bot newBot;
    String nome;
    
    public Chatterbot(String nome) {
        this.nome = nome;
        if (newBot == null) {
            //newBot = new Bot("camilabots", "C:\\Users\\Camila\\temp\\");
            newBot = new Bot("chatteredu", "C:/Users/user/Documents/201801/TCC1/testes");
        }

        if (chat == null) {
            chat = new Chat(newBot);
        }
    }

    public String getNome() {
        return nome;
    }

    public String getResposta(String mensagemUsuario) {
        String saida = chat.multisentenceRespond(mensagemUsuario);
        System.out.println(saida);
        return saida;
    }
}
