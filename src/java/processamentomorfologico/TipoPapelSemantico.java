package processamentomorfologico;

public enum TipoPapelSemantico {
	NENHUM(-1), AG(0), LOC(1), LOC_TMP(2), ORI_TMP(3), DES_TMP(4), EXT(5), EXT_TMP(6), TH(7);
	
	private final int valor;
	
	TipoPapelSemantico(int valorOpcao) {
		valor= valorOpcao;
	}
	
	public int getValor() {
		return valor;
	}
}
