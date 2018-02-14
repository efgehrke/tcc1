package processamentomorfologico;

public class Palavra {

	private String palavra;
	private String palavraBase;
	private String classificadoresSemanticos; // <civ>, <occ>, etc...
	private String classeGramatical;
	private String complementosDaClasseGramatical; // Gênero, número, grau, tempo, verbal, etc...
	private String classeSintatica; // Objeto direto, indireto, sujeito...
	private String papelSemantico; //§TH, §AG, §LOC-TMP...
	private boolean vozPassiva;
	
	/*
	 * EXEMPLO: Dançavam 
	 * palavra = Dançavam 
	 * palavraBase = dançar
	 * classificadoresSemanticos = <fmc> <vi> 
	 * classeGramatical = V
	 * complementosDaClasseGramatical = IMPF 3P IND VFIN 
	 * classeSintatica = @FMV
	 * (verbo principal)
	 */

	public Palavra() {
		super();
	}

	public Palavra(String palavra, String palavraBase, String classificadoresSemanticos,
			String classeGramatical, String complementosDaClasseGramatical, String classeSintatica, 
			String papelSemantico, boolean vozPassiva) {
		super();
		this.palavra = palavra;
		this.palavraBase = palavraBase;
		this.classificadoresSemanticos = classificadoresSemanticos;
		this.classeGramatical = classeGramatical;
		this.complementosDaClasseGramatical = complementosDaClasseGramatical;
		this.classeSintatica = classeSintatica;
		this.papelSemantico = papelSemantico;
		this.vozPassiva = vozPassiva;		
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public String getPalavraBase() {
		return palavraBase;
	}

	public void setPalavraBase(String palavraBase) {
		this.palavraBase = palavraBase;
	}

	public String getClassificadoresSemanticos() {
		return classificadoresSemanticos;
	}

	public void setClassificadoresSemanticos(String classificadoresSemanticos) {
		this.classificadoresSemanticos = classificadoresSemanticos;
	}

	public String getClasseGramatical() {
		return classeGramatical;
	}

	public void setClasseGramatical(String classeGramatical) {
		this.classeGramatical = classeGramatical;
	}

	public String getComplementosDaClasseGramatical() {
		return complementosDaClasseGramatical;
	}

	public void setComplementosDaClasseGramatical(String complementosDaClasseGramatical) {
		this.complementosDaClasseGramatical = complementosDaClasseGramatical;
	}

	public String getClasseSintatica() {
		return classeSintatica;
	}

	public void setClasseSintatica(String classeSintatica) {
		this.classeSintatica = classeSintatica;
	}
	
	public String getPapelSemantico() {
		return papelSemantico;
	}

	public void setPapelSemantico(String papelSemantico) {
		this.papelSemantico = papelSemantico;
	}
	
	public boolean getIsVozPassiva() {
		return vozPassiva;
	}

	public void setIsVozPassiva(boolean vozPassiva) {
		this.vozPassiva = vozPassiva;
	}
	
	public String getVerboPreteritoPerfeito() {
		String palavraBase = getPalavraBase();
		
		if (getClasseGramatical().equals("V")) {
			if(getPalavraBase().equalsIgnoreCase("ser") || getPalavraBase().equalsIgnoreCase("ir")) {
				return "foi";
			}
			
			if(getPalavraBase().equalsIgnoreCase("estar")) {
				return "esteve";
			}
			
			if(getPalavraBase().equalsIgnoreCase("dar")) {
				return "deu";
			}
						
			if(getTerminacaoVerboPalavraBase().equalsIgnoreCase("ar")) {
				palavraBase = palavraBase.substring(0, getPalavraBase().length()-2);
				palavraBase += "ou"; 
			}
			
			if(getTerminacaoVerboPalavraBase().equalsIgnoreCase("er")) {
				palavraBase = palavraBase.substring(0, getPalavraBase().length()-2);
				palavraBase += "eu";
			}
			
			if(getTerminacaoVerboPalavraBase().equalsIgnoreCase("ir")) {
				palavraBase = palavraBase.substring(0, getPalavraBase().length()-2);
				palavraBase += "iu";
			}
		}
		
		return palavraBase;
	}
	
	public String getVerboPreteritoImperfeito() {
		String palavra = getPalavra();
		
		if(getPalavraBase().equalsIgnoreCase("ser")) {
			return "era";
		}
		
		if (getPalavraBase().equalsIgnoreCase("ir")) {
			return "ia";
		}
		
		if (getClasseGramatical().equals("V")) {			
			if(getTerminacaoVerboPalavra().equalsIgnoreCase("am")) {  //ex: estavam, passeavam
				palavra = palavra.substring(0, palavra.length()-1);
			}			
		}
		
		return palavra;
	}
	
	private String getTerminacaoVerboPalavraBase() {
		if(getPalavraBase().length()-2 >= 0) {
			return getPalavraBase().substring(getPalavraBase().length()-2, getPalavraBase().length());
		} else {
			return getPalavraBase();
		}
	}
	
	private String getTerminacaoVerboPalavra() {
		if(getPalavra().length()-2 >= 0) {
			return getPalavra().substring(getPalavra().length()-2, getPalavra().length());
		} else {
			return getPalavra();
		}
	}
}
