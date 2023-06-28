package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ConsultaCep {
    public static Endereco consultarCep(String cep) {
        try {
            // Codifica o CEP para ser usado na URL da requisição
            String cepEncoded = URLEncoder.encode(cep, "UTF-8");
            String urlStr = "https://viacep.com.br/ws/" + cepEncoded + "/json/";

            // Faz a requisição HTTP para a API dos Correios
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Verifica se a requisição foi bem-sucedida (código de resposta 200)
            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Analisa a resposta JSON e obtém os dados do endereço
                String jsonResponse = response.toString();
                // Exemplo de código para extrair os dados do endereço do JSON
                String logradouro = obterValorCampoJson(jsonResponse, "logradouro");
                String numero = obterValorCampoJson(jsonResponse, "numero");
                String complemento = obterValorCampoJson(jsonResponse, "complemento");
                String bairro = obterValorCampoJson(jsonResponse, "bairro");
                String cidade = obterValorCampoJson(jsonResponse, "localidade");
                String uf = obterValorCampoJson(jsonResponse, "uf");

                // Retorna um objeto Endereco com os dados obtidos
                return new Endereco(cep, logradouro, numero, complemento, bairro, cidade, uf);
            } else {
                // Tratar o caso em que a requisição falhou
                System.out.println("Erro na requisição: " + conn.getResponseCode() + " - " + conn.getResponseMessage());
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Função auxiliar para obter o valor de um campo em um objeto JSON
    private static String obterValorCampoJson(String json, String campo) {
        int indiceCampo = json.indexOf("\"" + campo + "\":");
        if (indiceCampo == -1) {
            return "";
        }
        int indiceValor = json.indexOf("\"", indiceCampo + campo.length() + 3);
        int indiceFimValor = json.indexOf("\"", indiceValor + 1);
        return json.substring(indiceValor + 1, indiceFimValor);
    }
}
