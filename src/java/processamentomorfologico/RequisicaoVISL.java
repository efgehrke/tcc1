package processamentomorfologico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class RequisicaoVISL {
	
	public RequisicaoVISL() {
		
	}

	public ArrayList<String> getAnaliseMorfologicaVISLHtml(String textoEntrada, String processamento) throws Exception {
		ArrayList<String> arrayRetorno = new ArrayList<>();
		String url = "http://beta.visl.sdu.dk/visl/pt/parsing/automatic/parse.php?text=" + formatarEntrada(textoEntrada)
				+ "&parser=" + processamento + "&visual=niceline";

		AbstractHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		try {
			
			response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				if (line.contains("<dt>"))
					arrayRetorno.add(line);
			}
		} catch (IOException e) {			
                        //Antes era JOptionPane aqui
                        System.out.println("Falha ao realizar processamento. Verifique sua conexão com a internet.");
                        throw new Exception("Falha ao tentar se conectar ao site Palavras. Verificar conexão com a internet.");
		}

		return arrayRetorno;
	}

	private  String formatarEntrada(String texto) {
		texto = texto.replace("%", " por cento");
		return texto.replace(" ", "%20");
	}
}
