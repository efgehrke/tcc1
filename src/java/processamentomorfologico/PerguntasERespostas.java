package processamentomorfologico;

import java.util.ArrayList;

public class PerguntasERespostas {

	ArrayList<Palavra> sentenca;

	public PerguntasERespostas() {
		super();
	}

	public PerguntasERespostas(ArrayList<Palavra> sentenca) {
		super();
		this.sentenca = sentenca;
	}

	public ArrayList<Palavra> getSentenca() {
		return sentenca;
	}

	public void setSentenca(ArrayList<Palavra> sentenca) {
		this.sentenca = sentenca;
	}

	public StringBuilder criaPerguntasRespostas() throws Exception {
		String perguntaRespostaCurta = "";
		StringBuilder listaPerguntas = new StringBuilder();

		for (int i = 0; i < getSentenca().size(); i++) {
			perguntaRespostaCurta = processaPergunta(getTipoPapelSemantico(getSentenca().get(i)));

			if (!perguntaRespostaCurta.isEmpty()) {
				listaPerguntas.append(ajustesLinguisticos(perguntaRespostaCurta) + " // "
						+ ajustesLinguisticos(respostaLonga(getSentenca()).trim()) + "\n");
			}
		}

		return listaPerguntas;
	}

	private String respostaLonga(ArrayList<Palavra> frase) {
		String respostaLonga = "";

		for (int i = 0; i < frase.size(); i++) {
			respostaLonga += frase.get(i).getPalavra() + ' ';
		}

		return respostaLonga;
	}
	
	private String ajustesLinguisticos(String frase) {
		frase = ' ' + frase;

		frase = frase.replaceAll(" em a ", " na ");
		frase = frase.replaceAll(" em o ", " no ");
		frase = frase.replaceAll(" por a ", " pela ");
		frase = frase.replaceAll(" por o ", " pelo ");
                frase = frase.replaceAll(" por os ", " pelos ");
                frase = frase.replaceAll(" por as ", " pelas ");
		frase = frase.replaceAll(" de a ", " da ");
		frase = frase.replaceAll(" de o ", " do ");
		frase = frase.replaceAll(" em esta ", " nesta ");
		frase = frase.replaceAll(" em este ", " neste ");
		frase = frase.replaceAll(" em essa ", " nessa ");
		frase = frase.replaceAll(" em esse ", " nesse ");
		frase = frase.replaceAll(" a a ", " à ");
		frase = frase.replaceAll("  ", " ");
		frase = frase.replaceAll(" ,", ","); 
		frase = frase.replaceAll(" :", ":");
		frase = frase.replaceAll(" ;", ";");
		frase = frase.replaceAll(" \\.", "\\.");
		frase = frase.replaceAll(" \\?", "\\?");
		frase = frase.replaceAll("\\^ \\^ \\^", "\\^");
		frase = frase.replaceAll("\\^ \\^", "\\^");

		return frase.trim();
	}

	private TipoPapelSemantico getTipoPapelSemantico(Palavra palavra) {
		if (palavra.getPapelSemantico().equalsIgnoreCase("AG")) {
			return TipoPapelSemantico.AG;
		}

		if (palavra.getPapelSemantico().equalsIgnoreCase("LOC")) {
			return TipoPapelSemantico.LOC;
		}

		if (palavra.getPapelSemantico().equalsIgnoreCase("LOC-TMP")) {
			return TipoPapelSemantico.LOC_TMP;
		}

		if (palavra.getPapelSemantico().equalsIgnoreCase("ORI-TMP")) {
			return TipoPapelSemantico.ORI_TMP;
		}

		if (palavra.getPapelSemantico().equalsIgnoreCase("DES-TMP")) {
			return TipoPapelSemantico.DES_TMP;
		}

		if (palavra.getPapelSemantico().equalsIgnoreCase("EXT")) {
			return TipoPapelSemantico.EXT;
		}

		if (palavra.getPapelSemantico().equalsIgnoreCase("EXT-TMP")) {
			return TipoPapelSemantico.EXT_TMP;
		}

		if (palavra.getPapelSemantico().equalsIgnoreCase("TH")) {
			return TipoPapelSemantico.TH;
		}

		return TipoPapelSemantico.NENHUM;
	}

	private String processaPergunta(TipoPapelSemantico tipo) throws Exception {
		switch (tipo) {
		case AG:
			return perguntaAG();

		case LOC:
			return perguntaLOC();

		case LOC_TMP:
			return perguntaLOCTMP();

		case ORI_TMP:
			return perguntaORITMP();

		case DES_TMP:
			return perguntaDESTMP();

		case EXT:
			return perguntaEXT();

		case EXT_TMP:
			return perguntaEXTTMP();

		case TH:
			return perguntaTH();

		default:
			return "";
		}
	}

	private String perguntaAG() {
		String perguntaDeEntradaDoUsuario = "";
		String temaDaPerguntaDoChatterbot = "";		
		String perguntaDoChatterbotParaUsuario = "";
		String respostaCurta = "";
		ProcessaVerbo processaVerbo = new ProcessaVerbo();

		if (isVozPassiva()) {
			perguntaDeEntradaDoUsuario += " ^ por ^ quem ^ ";
			temaDaPerguntaDoChatterbot += " ^ sobre ^ por ^ quem ^ ";
			
			for (int i = 0; i < getSentenca().size(); i++) {
				if (getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("V")) {
					perguntaDoChatterbotParaUsuario += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
					
					if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
						perguntaDeEntradaDoUsuario += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
						temaDaPerguntaDoChatterbot += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
					} else {
						perguntaDeEntradaDoUsuario += " ^ ";
						temaDaPerguntaDoChatterbot += " ^ ";
					}
				} else {
					perguntaDoChatterbotParaUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
					
					if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
						perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
						temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
					} else {
						perguntaDeEntradaDoUsuario += " ^ ";
						temaDaPerguntaDoChatterbot += " ^ ";
					}
				}
			}			
			
			respostaCurta = getSujeitoOracao("AG").trim();			
			perguntaDoChatterbotParaUsuario = perguntaDoChatterbotParaUsuario.replace(respostaCurta, "por quem");
			
		} else {
			perguntaDeEntradaDoUsuario = "^ quem ^ ";
			temaDaPerguntaDoChatterbot = "^ sobre ^ quem ^ ";	
			perguntaDoChatterbotParaUsuario += "quem ";

			for (int i = 0; i < getSentenca().size(); i++) {
				if (getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("V")) {
					perguntaDoChatterbotParaUsuario += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
					
					if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
						perguntaDeEntradaDoUsuario += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
						temaDaPerguntaDoChatterbot += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
					} else {
						perguntaDeEntradaDoUsuario += " ^ ";
						temaDaPerguntaDoChatterbot += " ^ ";
					}
				} else {
					perguntaDoChatterbotParaUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
					
					if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
						perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
						temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
					} else {
						perguntaDeEntradaDoUsuario += " ^ ";
						temaDaPerguntaDoChatterbot += " ^ ";
					}
				}
			}

			respostaCurta = getSujeitoOracao("AG").trim();
			perguntaDoChatterbotParaUsuario = perguntaDoChatterbotParaUsuario.replace(respostaCurta, "");
		}
		
                perguntaDeEntradaDoUsuario += " ^";
                temaDaPerguntaDoChatterbot += " ^";
		return substituiRespostas(perguntaDeEntradaDoUsuario, temaDaPerguntaDoChatterbot, respostaCurta) + 
				perguntaDoChatterbotParaUsuario;
	}

	private String perguntaLOC() {		
		String perguntaDeEntradaDoUsuario = "onde ^ ";
		String temaDaPerguntaDoChatterbot = "^ sobre ^ onde ";
		String perguntaDoChatterbotParaUsuario = "onde";

		for (int i = 0; i < getSentenca().size(); i++) {
			perguntaDoChatterbotParaUsuario += ' ' + getSentenca().get(i).getPalavra();
			
			if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
				perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
				temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
			} else {
				perguntaDeEntradaDoUsuario += " ^ ";
				temaDaPerguntaDoChatterbot += " ^ ";
			}
		}

		String respostaCurta = getSujeitoOracao("LOC").trim();	
                perguntaDeEntradaDoUsuario += " ^";
                temaDaPerguntaDoChatterbot += " ^";
		return substituiRespostas(perguntaDeEntradaDoUsuario, temaDaPerguntaDoChatterbot, respostaCurta) + 
				perguntaDoChatterbotParaUsuario.replace(respostaCurta, "");
	}

	private String perguntaLOCTMP() {
		String perguntaDeEntradaDoUsuario = "quando ^ ";
		String temaDaPerguntaDoChatterbot = "^ sobre ^ quando ";
		String perguntaDoChatterbotParaUsuario = "quando";

		for (int i = 0; i < getSentenca().size(); i++) {
			perguntaDoChatterbotParaUsuario += ' ' + getSentenca().get(i).getPalavra().trim();
			
			if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
				perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
				temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
			} else {
				perguntaDeEntradaDoUsuario += "^ ";
				temaDaPerguntaDoChatterbot += "^ ";
			}
		}

		String respostaCurta = getLocTmp().trim();	
                perguntaDeEntradaDoUsuario += " ^";
                temaDaPerguntaDoChatterbot += " ^";
		return substituiRespostas(perguntaDeEntradaDoUsuario, temaDaPerguntaDoChatterbot, respostaCurta) +
				perguntaDoChatterbotParaUsuario.replace(respostaCurta, "");
	}

	private String perguntaORITMP() {
		String perguntaDeEntradaDoUsuario = "^ desde ^ quando ^ ";
		String temaDaPerguntaDoChatterbot = "^ sobre ^ desde ^ quando ^ ";
		String perguntaDoChatterbotParaUsuario = "";

		for (int i = 0; i < getSentenca().size(); i++) {
			perguntaDoChatterbotParaUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
			
			if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
				perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
				temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
			} else {
				perguntaDeEntradaDoUsuario += "^ ";
				temaDaPerguntaDoChatterbot += "^ ";
			}
		}		
		
		String respostaCurta = getOriTmp().trim();			
                perguntaDeEntradaDoUsuario += " ^";
                temaDaPerguntaDoChatterbot += " ^";
		return substituiRespostas(perguntaDeEntradaDoUsuario, temaDaPerguntaDoChatterbot, respostaCurta) +
				perguntaDoChatterbotParaUsuario.replace(respostaCurta, "desde quando").trim();
	}

	private String perguntaDESTMP() {
		return "";
	}

	private String perguntaEXT() {
		String perguntaDeEntradaDoUsuario = "";
		String temaDaPerguntaDoChatterbot = "";
		String perguntaDoChatterbotParaUsuario = "";
		String question = "quanto";

		for (int i = 0; i < getSentenca().size(); i++) {
			if (getSentenca().get(i).getClassificadoresSemanticos().contains("temp")) {
				question = "quando";
			}

			perguntaDoChatterbotParaUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
			
			if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
				perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
				temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
			} else {
				perguntaDeEntradaDoUsuario += "^ ";
				temaDaPerguntaDoChatterbot += "^ ";
			}
		}
		
		perguntaDeEntradaDoUsuario = "^ " + question + " ^ " + perguntaDeEntradaDoUsuario;
		temaDaPerguntaDoChatterbot = "^ sobre ^ " + perguntaDeEntradaDoUsuario;

		String respostaCurta = getSujeitoOracao("EXT").trim();		
                perguntaDeEntradaDoUsuario += " ^";
                temaDaPerguntaDoChatterbot += " ^";
		return substituiRespostas(perguntaDeEntradaDoUsuario, temaDaPerguntaDoChatterbot, respostaCurta) +
				perguntaDoChatterbotParaUsuario.replace(respostaCurta, question).trim();
	}

	private String perguntaEXTTMP() {
		String perguntaDeEntradaDoUsuario = "^ quanto ^ tempo ^ ";
		String temaDaPerguntaDoChatterbot = "^ sobre ^ quanto ^ tempo ^ ";
		String perguntaDoChatterbotParaUsuario = "";

		for (int i = 0; i < getSentenca().size(); i++) {
			perguntaDoChatterbotParaUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
			
			if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
				perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
				temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
			} else {
				perguntaDeEntradaDoUsuario += "^ ";
				temaDaPerguntaDoChatterbot += "^ ";
			}
		}

		String respostaCurta = getSujeitoOracao("EXT-TMP").trim();	
                perguntaDeEntradaDoUsuario += " ^";
                temaDaPerguntaDoChatterbot += " ^";
		return substituiRespostas(perguntaDeEntradaDoUsuario, temaDaPerguntaDoChatterbot, respostaCurta) +
				perguntaDoChatterbotParaUsuario.replace(respostaCurta, "quanto tempo").trim();
	}

	private String perguntaTH() throws Exception {
		String perguntaDeEntradaDoUsuario = "";
		String temaDaPerguntaDoChatterbot = "";
		String perguntaDoChatterbotParaUsuario = "";
		ProcessaVerbo processaVerbo = new ProcessaVerbo();

		for (int i = 0; i < getSentenca().size(); i++) {
			if (getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("V")) {
				perguntaDoChatterbotParaUsuario += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
				
				if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
					perguntaDeEntradaDoUsuario += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
					temaDaPerguntaDoChatterbot += processaVerbo.getVerbo3PessoaSingular(getSentenca().get(i)) + ' ';
				} else {
					perguntaDeEntradaDoUsuario += "^ ";
					temaDaPerguntaDoChatterbot += "^ ";
				}
			} else {
				perguntaDoChatterbotParaUsuario += getSentenca().get(i).getPalavra() + ' ';
				
				if (!getSentenca().get(i).getPapelSemantico().isEmpty()) {
					perguntaDeEntradaDoUsuario += getSentenca().get(i).getPalavra().trim() + ' ';
					temaDaPerguntaDoChatterbot += getSentenca().get(i).getPalavra().trim() + ' ';
				} else {
					perguntaDeEntradaDoUsuario += "^ ";
					temaDaPerguntaDoChatterbot += "^ ";
				}
			}
		}

		String respostaCurta = getSujeitoOracao("TH").trim();
		String question = getInterrogativaTH();
		perguntaDeEntradaDoUsuario = "^ " + question + " ^ " + perguntaDeEntradaDoUsuario + " ^";
		temaDaPerguntaDoChatterbot = "^ sobre ^ " + question + " ^ " + temaDaPerguntaDoChatterbot + " ^";
		
		return substituiRespostas(perguntaDeEntradaDoUsuario, temaDaPerguntaDoChatterbot, respostaCurta) +
				perguntaDoChatterbotParaUsuario.replace(respostaCurta, question).trim();
	}
	
	private String substituiRespostas(String perguntaDeEntradaDoUsuario, String temaDaPerguntaDoChatterbot, String respostaCurta) {
		String palavra = "";
		String respostaCurtaArray[] = respostaCurta.split(" ");
		for (int i = 0; i < respostaCurtaArray.length; i++) {
			palavra = ' ' + respostaCurtaArray[i] + ' ';
			
			if(perguntaDeEntradaDoUsuario.contains(palavra)) {
				perguntaDeEntradaDoUsuario = perguntaDeEntradaDoUsuario.replaceAll(palavra, " ^ ");
			}
			
			if(temaDaPerguntaDoChatterbot.contains(palavra)) {
				temaDaPerguntaDoChatterbot = temaDaPerguntaDoChatterbot.replaceAll(palavra, " ^ ");
			}
		}
		
		return perguntaDeEntradaDoUsuario.trim() + " // " + respostaCurta + " // " + temaDaPerguntaDoChatterbot.trim() + " // ";
	}

	private String getInterrogativaTH() throws Exception {
		for (int i = 0; i < getSentenca().size(); i++) {
                    if (getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("PERS")) {
                            return "quem";
                    } else {
			if (getSentenca().get(i).getPapelSemantico().equalsIgnoreCase("TH")) {
                            if (isHumano(getSentenca().get(i).getPalavra())) {
                                return "quem";
                            }
			} else if (getSentenca().get(i).getPalavraBase().equalsIgnoreCase("SER")) {
                            if (i - 1 >= 0) {
                                if (getSentenca().get(i - 1).getPapelSemantico().equalsIgnoreCase("TH")) {
                                    return "qual";
                                }
                            }
			}
                    }
		}
		
		return "o que";
	}

	private boolean isHumano(String palavraTH) throws Exception {
		ProcessaHtmlVISL processaVISL = new ProcessaHtmlVISL();
		ArrayList<Palavra> sentenca = processaVISL.transformaSentencaEmObjetoPalavra(palavraTH, "parse");

		for (int i = 0; i < sentenca.size(); i++) {
			// Verifica se tem algum classificador humano
			if (getSentenca().get(i).getClassificadoresSemanticos().contains("<H>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<HH>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Hattr>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Hbio>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Hideo>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Hmyth>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Hnat>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Hprof>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Hsick>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<Htit>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<hum>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<official>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<member>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<groupind>")
					|| getSentenca().get(i).getClassificadoresSemanticos().contains("<groupofficial>")) {
				return true;
			}
		}

		return false;
	}

	private boolean isVozPassiva() {
		for (int i = 0; i < getSentenca().size(); i++) {
			if (getSentenca().get(i).getIsVozPassiva()) {
				return true;
			}
		}

		return false;
	}

	private String getLocTmp() {
		ArrayList<Palavra> arrayPalavras = new ArrayList<>();
		String retorno = "";

		for (int i = 0; i < getSentenca().size(); i++) {
			if (getSentenca().get(i).getPapelSemantico().equalsIgnoreCase("LOC-TMP")
					|| getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("PRP")
					|| getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("NUM")
					|| getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("ADV")) {

				// Verifica se é a primeira palavra da frase
				if (i - 1 < 0) {
					// Se for a primeira palavra, deve verificar se a palavra
					// seguinte se enquandra na regra: PRP, NUM, ADV ou LOC-TMP
					if (i + 1 < getSentenca().size()) {
						arrayPalavras.add(getSentenca().get(i));
					}
				} else { // Se não for a primeira palavra da frase deve ser
							// verificar se as palavras anteriores ou
							// posteriores obedecem a regra
					if (getSentenca().get(i - 1).getPapelSemantico().equalsIgnoreCase("LOC-TMP")
							|| getSentenca().get(i - 1).getClasseGramatical().equalsIgnoreCase("PRP")
							|| getSentenca().get(i - 1).getClasseGramatical().equalsIgnoreCase("NUM")
							|| getSentenca().get(i - 1).getClasseGramatical().equalsIgnoreCase("ADV")) {
						arrayPalavras.add(getSentenca().get(i));
					} else if (i + 1 < getSentenca().size()) {
						if (getSentenca().get(i + 1).getPapelSemantico().equalsIgnoreCase("LOC-TMP")
								|| getSentenca().get(i + 1).getClasseGramatical().equalsIgnoreCase("PRP")
								|| getSentenca().get(i + 1).getClasseGramatical().equalsIgnoreCase("NUM")
								|| getSentenca().get(i + 1).getClasseGramatical().equalsIgnoreCase("ADV")) {
							arrayPalavras.add(getSentenca().get(i));
						} else if (getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("PRP")) { // Caso
																											// com
																											// PRP
																											// +
																											// DET.
																											// Ex:
																											// Em
																											// a
																											// =
																											// na.
							if (getSentenca().get(i + 1).getClasseGramatical().equalsIgnoreCase("DET")) {
								arrayPalavras.add(getSentenca().get(i));
								arrayPalavras.add(getSentenca().get(i + 1));
							}
						}
					}
				}
			}
		}

		// Tratamento para não retirar o "POR" no fim do sujeito. Exemplo: O
		// Brasil foi descoberto em 1500 por Pedro - fica "em 1500 por" e não
		// pode retirar o "por".
		if (arrayPalavras.get(arrayPalavras.size() - 1).getPalavra().equalsIgnoreCase("por")) {
			arrayPalavras.remove(arrayPalavras.size() - 1);
		}

		for (int i = 0; i < arrayPalavras.size(); i++) {
			retorno += arrayPalavras.get(i).getPalavra() + ' ';
		}

		return retorno.trim();
	}

	private String getOriTmp() {
		ArrayList<Palavra> arrayPalavras = new ArrayList<>();

		for (int i = 0; i < getSentenca().size(); i++) {
			if (getSentenca().get(i).getPalavra().equalsIgnoreCase("desde")) {
				while (i < getSentenca().size() && !getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("V")) {
					arrayPalavras.add(getSentenca().get(i));
					i++;
				}
			}
		}

		return palavrasToString(arrayPalavras);
	}

	private String getSujeitoOracao(String papelSemantico) {
		ArrayList<Palavra> arrayPalavras = new ArrayList<>();

		for (int i = 0; i < getSentenca().size(); i++) {
			if (getSentenca().get(i).getClasseGramatical().equalsIgnoreCase("V")) {
				if (encontrouPapelSemantico(arrayPalavras, papelSemantico)) {
					return palavrasToString(arrayPalavras);
				} else {
					arrayPalavras.removeAll(arrayPalavras);
				}
			} else {
				arrayPalavras.add(getSentenca().get(i));
			}
		}

		return palavrasToString(arrayPalavras);
	}

	private boolean encontrouPapelSemantico(ArrayList<Palavra> palavras, String papelSemantico) {
		for (int i = 0; i < palavras.size(); i++) {
			if (palavras.get(i).getPapelSemantico().equalsIgnoreCase(papelSemantico)) {
				return true;
			}
		}

		return false;
	}

	private String palavrasToString(ArrayList<Palavra> palavras) {
		String retornoStr = "";

		for (int i = 0; i < palavras.size(); i++) {
			retornoStr += palavras.get(i).getPalavra() + ' ';
		}

		return retornoStr;
	}
}
