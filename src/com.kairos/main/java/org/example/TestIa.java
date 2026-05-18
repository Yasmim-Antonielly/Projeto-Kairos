package org.example;

import service.IaService;

public class TestIa {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("TESTANDO INTEGRAÇÃO COM INTELIGÊNCIA ARTIFICIAL  ");
        System.out.println("=================================================");

        // 1. Instancia o serviço de IA
        IaService iaService = new IaService();

        // 2. Cria um relato fictício não estruturado para teste
        String relatoTeste = "O projeto social Coders da Quebrada formou 42 jovens da periferia em desenvolvimento web. "
                + "Gastamos R$ 30.000,00 em notebooks recondicionados e R$ 15.000,00 em infraestrutura. "
                + "Até o momento, 18 jovens já conseguiram emprego como desenvolvedores júnior, mudando a realidade financeira de suas famílias.";

        System.out.println("\n[1/2] Enviando relato teste para a IA (Groq Llama 3.1)...");
        System.out.println("Relato Enviado: \"" + relatoTeste + "\"");

        // 3. Executa a chamada de IA
        long inicio = System.currentTimeMillis();
        String respostaIa = iaService.analisarRelato(relatoTeste);
        long fim = System.currentTimeMillis();

        // 4. Imprime os resultados
        System.out.println("\n[2/2] Resposta recebida com sucesso em " + (fim - inicio) + "ms!");
        System.out.println("\n----------------- RELATÓRIO DO AUDITOR IA -----------------");
        System.out.println(respostaIa);
        System.out.println("-----------------------------------------------------------");
    }
}
