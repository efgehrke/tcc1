package chatterbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang3.StringUtils;

public class ConstrutorAIML {
	private StringBuilder perguntasRespostas;
	private String temaDaPerguntaDoChatterbot;
	private String perguntaDoChatterbotParaUsuario;
	private String perguntaDeEntradaDoUsuario;
	private String respostaCurta;
	private String respostaLonga;
        
	public ConstrutorAIML(StringBuilder perguntasRespostas) {
		this.perguntasRespostas = perguntasRespostas;
	}

	public StringBuilder getPerguntasRespostas() {
		return perguntasRespostas;
	}

	public void setPerguntasRespostas(StringBuilder perguntasRespostas) {
		this.perguntasRespostas = perguntasRespostas;
	}

	public String getTemaDaPerguntaDoChatterbot() {
		return temaDaPerguntaDoChatterbot;
	}

	public void setTemaDaPerguntaDoChatterbot(String temaDaPerguntaDoChatterbot) {
		this.temaDaPerguntaDoChatterbot = temaDaPerguntaDoChatterbot;
	}

	public String getPerguntaDoChatterbotParaUsuario() {
		return perguntaDoChatterbotParaUsuario;
	}

	public void setPerguntaDoChatterbotParaUsuario(String perguntaDoChatterbotParaUsuario) {
		this.perguntaDoChatterbotParaUsuario = perguntaDoChatterbotParaUsuario;
	}

	public String getPerguntaDeEntradaDoUsuario() {
		return perguntaDeEntradaDoUsuario;
	}

	public void setPerguntaDeEntradaDoUsuario(String perguntaDeEntradaDoUsuario) {
		this.perguntaDeEntradaDoUsuario = perguntaDeEntradaDoUsuario;
	}

	public String getRespostaCurta() {
		return respostaCurta;
	}

	public void setRespostaCurta(String respostaCurta) {
		this.respostaCurta = respostaCurta;
	}

	public String getRespostaLonga() {
		return respostaLonga;
	}

	public void setRespostaLonga(String respostaLonga) {
		this.respostaLonga = respostaLonga;
	}

	public void criarBaseAIML() {		
		StringBuilder aiml = new StringBuilder();
                ArrayList<String> perguntas = new ArrayList<>();
		aiml.append(getCabecalho().toString());

		// processa cada tipo de pergunta separadamente.
		String[] temp = getPerguntasRespostas().toString().split("\n");
		for (int i = 0; i < temp.length; i++) {
                        processaFrasesEntrada(temp[i]);
			aiml.append(getCategoryPerguntaUsuario().toString());
			aiml.append(getCategoryTemaUsuario().toString());
			aiml.append(getCategoryRespostaCertaUsuario().toString());
                        aiml.append(getCategoryRespostaErradaUsuario().toString());
                        perguntas.add(getPerguntaDoChatterbotParaUsuario()+"?");
		}

                aiml.append(getCategoryPerguntasGenericas(perguntas));
		aiml.append(getRodape().toString());

		try {
                    
                    //Importante! Trocar FileWriter por BufferedWriter!
			File fl = new File("C:/Users/user/Documents/201801/TCC1/testes/bots/chatteredu/aiml/BaseConhecimento.aiml");
			fl.createNewFile();
                        
                        //java.io.OutputStream os = new FileOutputStream(fl);
                        OutputStreamWriter bufferedWriter = new OutputStreamWriter(new FileOutputStream(fl), "UTF-8"); 
                        //ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(aiml.toString());
                        bufferedWriter.write(aiml.toString());
                        
                        System.out.println("===== AILM =====\n" + aiml.toString());
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        //ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(aiml.toString());
                        //FileWriter fw = new FileWriter(fl);
                        //fw.write(byteBuffer.toString());

                        //File fl = builder.parse(new File("C:/Users/user/Documents/201801/TCC1/testes/bots/chatteredu/aiml/BaseConhecimento.aiml"));
                        //fl.createNewFile();
                        /*FileWriter fw = new FileWriter(fl);
                        fw.write(aiml.toString());
                        fw.flush();
			fw.close();*/
                        
		} catch (IOException e) {
			e.getMessage();
		}
	}

	private void processaFrasesEntrada(String frase) {
		String[] entrada = frase.split(" // ");
		
		setPerguntaDeEntradaDoUsuario(StringUtils.capitalize(entrada[0]));
		setRespostaCurta(StringUtils.capitalize(entrada[1]));
		setTemaDaPerguntaDoChatterbot(StringUtils.capitalize(entrada[2]));
		setPerguntaDoChatterbotParaUsuario(StringUtils.capitalize(entrada[3]));
		setRespostaLonga(StringUtils.capitalize(entrada[4]));
	}

	private StringBuilder getCabecalho() {
		StringBuilder cabecalho = new StringBuilder();

		cabecalho.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		cabecalho.append("\n\n");
		cabecalho.append("<aiml>");
		cabecalho.append("\n\n");

		return cabecalho;
	}
	
	private StringBuilder getCategoryPerguntaUsuario() {
		StringBuilder category = new StringBuilder();
		category.append("<category>");
		category.append("\n");

		category.append("<pattern>");
		category.append(getPerguntaDeEntradaDoUsuario().trim());
		category.append("</pattern>");
		category.append("\n");

		category.append("<template>");
		category.append("\n");
		category.append("<random>");
		category.append("\n");
		category.append("<li>");
		category.append(getRespostaCurta());
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append(getRespostaLonga().replace("\n", ""));
		category.append("</li>");
		category.append("\n");
		category.append("</random>");
		category.append("\n");
		category.append("</template>");
		category.append("\n");

		category.append("</category>");
		category.append("\n");
		category.append("\n");

		return category;
	}
	
	private StringBuilder getCategoryTemaUsuario() {
		StringBuilder category = new StringBuilder();
		category.append("<category>");
		category.append("\n");

		category.append("<pattern>");
		category.append(getTemaDaPerguntaDoChatterbot());
		category.append("</pattern>");
		category.append("\n");

		category.append("<template>");
		category.append(getPerguntaDoChatterbotParaUsuario()+"?");
		category.append("</template>");
		category.append("\n");

		category.append("</category>");
		category.append("\n");
		category.append("\n");

		return category;
	}
	
	private StringBuilder getCategoryRespostaCertaUsuario() {
		StringBuilder category = new StringBuilder();
		category.append("<category>");
		category.append("\n");

		category.append("<pattern>");
		category.append("^ " + getRespostaCurta() + " ^");
		category.append("</pattern>");
		category.append("\n");
		
		category.append("<that>");
		category.append(getPerguntaDoChatterbotParaUsuario().replace("?", "")); //Sem o ponto de interrogação
		category.append("</that>");
		category.append("\n");

		category.append("<template>");		
		category.append("\n");
		category.append("<random>");
		category.append("\n");
		category.append("<li>");
		category.append("Isso mesmo! Você está indo muito bem nos estudos!");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Parabéns, é isso mesmo!");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Está correto! Parabéns!");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Acertou em cheio hein!");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Opa, acertou! Você é fera!");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Está certo! Desse jeito você vai tirar dez na prova deste assunto!");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Certíssimo, meus parabéns!");
		category.append("</li>");
		category.append("\n");
		category.append("</random>");
		category.append("\n");
		category.append("</template>");
		category.append("\n");

		category.append("</category>");
		category.append("\n");
		category.append("\n");

		return category;
	}
        
        private StringBuilder getCategoryRespostaErradaUsuario() {
		StringBuilder category = new StringBuilder();
		category.append("<category>");
		category.append("\n");

		category.append("<pattern>");
		category.append("^");
		category.append("</pattern>");
		category.append("\n");
		
		category.append("<that>");
		category.append(getPerguntaDoChatterbotParaUsuario().replace("?", "")); //Sem o ponto de interrogação
		category.append("</that>");
		category.append("\n");

		category.append("<template>");		
		category.append("\n");
		category.append("<random>");
		category.append("\n");
		category.append("<li>");
		category.append("Ish, na realidade não é bem isso... " + getRespostaLonga() + ".");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Ah, não foi dessa vez! " + getRespostaLonga() + ".");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Puxa, não é essa a resposta correta... " + getRespostaLonga() + ".");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Ops, dessa vez você não acertou. " + getRespostaLonga() + ".");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Na realidade não é esta a resposta correta. " + getRespostaLonga() + ".");
		category.append("</li>");
		category.append("\n");
		category.append("<li>");
		category.append("Ih, não é isto que está no seu texto! " + getRespostaLonga() + ".");
		category.append("</li>");
		category.append("\n");
		category.append("</random>");
		category.append("\n");
		category.append("</template>");
		category.append("\n");

		category.append("</category>");
		category.append("\n");
		category.append("\n");

		return category;
	}
        
        private StringBuilder getCategoryPerguntasGenericas(ArrayList<String> perguntas) {
		StringBuilder category = new StringBuilder();
		category.append("<category>");
		category.append("\n");
		category.append("<pattern>");
		category.append("^ sobre ^ texto ^");
		category.append("</pattern>");
		category.append("\n");
                category.append("<template>");
		category.append("\n");
		category.append("<random>");
		category.append("\n");
                
                for(int i = 0; i < perguntas.size(); i++) {
                    category.append("<li>");
                    category.append(perguntas.get(i));
                    category.append("</li>");
                    category.append("\n");
                }
		
		category.append("</random>");
		category.append("\n");
		category.append("</template>");
		category.append("\n");
		category.append("</category>");
		category.append("\n");
		category.append("\n");

		return category;
	}

	private static StringBuilder getRodape() {
		StringBuilder rodape = new StringBuilder();
		rodape.append("</aiml>");

		return rodape;
	}
}
