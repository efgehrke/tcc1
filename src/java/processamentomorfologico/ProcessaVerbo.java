package processamentomorfologico;

public class ProcessaVerbo {

	public String getVerbo3PessoaSingular(Palavra palavra) {
		boolean isPreteritoPerfeito = false;
		boolean isPassado = false;
		String verbo = palavra.getPalavra();

		// Só faz o tratamento verbal quando estiver no plural
		if (palavra.getComplementosDaClasseGramatical().contains("1P")
				|| palavra.getComplementosDaClasseGramatical().contains("2P")
				|| palavra.getComplementosDaClasseGramatical().contains("3P")) {
			// Pretérito perfeito
			if (palavra.getComplementosDaClasseGramatical().contains("PS")) {
				isPreteritoPerfeito = true;
			}

			// Presente, pretérito imperfeito ou pretérito-mais-que-perfeito
			if (palavra.getComplementosDaClasseGramatical().contains("PR")
					|| palavra.getComplementosDaClasseGramatical().contains("IMPF")
					|| palavra.getComplementosDaClasseGramatical().contains("MQP")) {
				isPassado = true;
			}

			if (isPreteritoPerfeito) {
				verbo = palavra.getVerboPreteritoPerfeito();
			} else if (isPassado) {
				verbo = palavra.getVerboPreteritoImperfeito();
			}
		}

		return verbo;
	}
}
