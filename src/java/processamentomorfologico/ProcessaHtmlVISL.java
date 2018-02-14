package processamentomorfologico;

import java.util.ArrayList;

import chatterbot.ConstrutorAIML;
import javax.swing.JOptionPane;

public class ProcessaHtmlVISL {

	public boolean processaTexto(String texto) throws Exception {
            StringBuilder builderPerguntas = new StringBuilder();
            PerguntasERespostas perguntas = new PerguntasERespostas();
            texto = texto.replace(".", "_");
            String[] sentencas = texto.split ("_");

            for (String sentenca : sentencas) {
                perguntas.setSentenca(transformaSentencaEmObjetoPalavra(sentenca, "roles"));
                builderPerguntas.append(perguntas.criaPerguntasRespostas().toString());
            }
            
		
            if (builderPerguntas.length() > 0) {
                ConstrutorAIML aiml = new ConstrutorAIML(builderPerguntas);
                aiml.criarBaseAIML();
                return true;
            } else {
                //antes era JOption Pane aqui
                System.out.println("Não foi possível realizar o processamento. Texto inserido inválido.");
                return false;
            }
	}

	public ArrayList<Palavra> transformaSentencaEmObjetoPalavra(String sentenca, String processamento) throws Exception {
		RequisicaoVISL req = new RequisicaoVISL();
		ArrayList<String> retornoHtml = req.getAnaliseMorfologicaVISLHtml(sentenca, processamento);
		ArrayList<Palavra> novasPalavras = new ArrayList<Palavra>();

		for (int i = 0; i < retornoHtml.size(); i++) {
			Palavra novaPalavra = new Palavra();
			novaPalavra.setPalavra(extrairElemento(retornoHtml.get(i), 3));
			novaPalavra.setPalavraBase(extrairPalavraBase(retornoHtml.get(i)));
			novaPalavra.setClassificadoresSemanticos(retiraLTeGT(extrairElemento(retornoHtml.get(i), 7).trim()));
			novaPalavra.setClasseGramatical(extrairElemento(retornoHtml.get(i), 9));
			novaPalavra.setComplementosDaClasseGramatical(extrairElemento(retornoHtml.get(i), 10).trim());
			novaPalavra.setClasseSintatica(substituirLTeGT(extrairElemento(retornoHtml.get(i), 12)));
			novaPalavra.setPapelSemantico(extrairPapelSemantico(retornoHtml.get(i)));
			novaPalavra.setIsVozPassiva(isVozPassiva(retornoHtml.get(i)));

			novasPalavras.add(novaPalavra);
		}

		return novasPalavras;
	}

	private String extrairElemento(String sentencaHtml, int qtdTags) {
		int cont = 0;

		for (int i = 0; i < sentencaHtml.length(); i++) {
			if (sentencaHtml.charAt(i) == '>') {
				cont++;
			}

			if (cont == qtdTags) {
				for (int j = i + 1; j < sentencaHtml.length(); j++) {
					if (sentencaHtml.charAt(j) == '<') {
						return sentencaHtml.substring(i + 1, j);
					}
				}
			}
		}

		return "";
	}

	private String extrairPalavraBase(String sentencaHtml) {
		for (int j = sentencaHtml.indexOf('[') + 1; j < sentencaHtml.length(); j++) {
			if (sentencaHtml.charAt(j) == ']') {
				return sentencaHtml.substring(sentencaHtml.indexOf('[') + 1, j);
			}
		}

		return "";
	}

	private String extrairPapelSemantico(String sentencaHtml) {
		// Garante que a palavra tem um papel semântico
		if(sentencaHtml.indexOf("§") >= 0) {
			for (int i = sentencaHtml.indexOf("§") + 1; i < sentencaHtml.length(); i++) {
				if (sentencaHtml.charAt(i) == ' ') {
					return sentencaHtml.substring(sentencaHtml.indexOf("§") + 1, i);
				}
			}
		}

		return "";
	}

	private boolean isVozPassiva(String sentencaHTML) {
		return sentencaHTML.contains("PASS") || sentencaHTML.contains("PAS");
	}

	private String retiraLTeGT(String classificadorSemantico) {
		classificadorSemantico = classificadorSemantico.replace("&lt;", "");
		classificadorSemantico = classificadorSemantico.replace("&gt;", "");

		return classificadorSemantico;
	}

	private String substituirLTeGT(String classeSintatica) {
		classeSintatica = classeSintatica.replace("&lt;", "<");
		classeSintatica = classeSintatica.replace("&gt;", ">");

		return classeSintatica;
	}
}
