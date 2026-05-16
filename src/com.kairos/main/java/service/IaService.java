package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class IaService {

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private final HttpClient client = HttpClient.newHttpClient();

    public String analisarRelato(String relato) {
        if (relato == null || relato.isBlank()) {
            return "Relato não informado. Análise indisponível.";
        }

        try {
            String prompt = "Você é um auditor especialista em impacto social e SROI. "
                    + "Analise o relato abaixo de um projeto social e extraia: "
                    + "1) Número estimado de pessoas beneficiadas, "
                    + "2) Tipo de impacto principal, "
                    + "3) Nível de escalabilidade, "
                    + "4) Pontos fortes, "
                    + "5) Estimativa do valor social gerado. "
                    + "Relato: " + relato;

            String body = "{\"model\":\"llama-3.1-8b-instant\","
                    + "\"messages\":[{\"role\":\"user\",\"content\":\""
                    + prompt.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "")
                    + "\"}]}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            String rb = response.body();
            System.out.println("Resposta Groq: " + rb);

            String marker = "\"content\":\"";
            int idx = rb.lastIndexOf(marker);
            if (idx != -1) {
                int start = idx + marker.length();
                StringBuilder result = new StringBuilder();
                int i = start;
                while (i < rb.length()) {
                    char c = rb.charAt(i);
                    if (c == '\\' && i + 1 < rb.length()) {
                        char next = rb.charAt(i + 1);
                        if (next == '"') { result.append('"'); i += 2; continue; }
                        if (next == 'n') { result.append('\n'); i += 2; continue; }
                        if (next == '\\') { result.append('\\'); i += 2; continue; }
                    }
                    if (c == '"') break;
                    result.append(c);
                    i++;
                }
                return result.toString();
            }
            return "Não foi possível extrair análise da IA.";

        } catch (Exception e) {
            System.out.println("Erro IA: " + e.getMessage());
            return "Erro ao chamar IA: " + e.getMessage();
        }
    }
}