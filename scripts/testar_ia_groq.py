#!/usr/bin/env python
# -*- coding: utf-8 -*-

import urllib.request
import json

def testar_ia():
    api_key = "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"
    url = "https://api.groq.com/openai/v1/chat/completions"
    
    prompt = "Você é um auditor especialista em impacto social e SROI. Analise o relato abaixo e diga os 3 principais pontos fortes: 'Curso de programação web gratuito formou 42 jovens na favela e 18 já estão empregados.'"
    
    body = {
        "model": "llama-3.1-8b-instant",
        "messages": [{"role": "user", "content": prompt}]
    }
    
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {api_key}"
    }
    
    req = urllib.request.Request(
        url, 
        data=json.dumps(body).encode('utf-8'), 
        headers=headers, 
        method='POST'
    )
    
    print("==================================================")
    print("TESTANDO INTEGRAÇÃO DIRETA COM A IA (GROQ/LLAMA)  ")
    print("==================================================")
    print("[1/2] Enviando requisição de teste para Groq API...")
    
    try:
        with urllib.request.urlopen(req) as response:
            res_body = response.read().decode('utf-8')
            res_json = json.loads(res_body)
            resposta = res_json['choices'][0]['message']['content']
            
            print("[2/2] Resposta da IA recebida com sucesso!")
            print("\n----------- AVALIAÇÃO DA IA (TESTE) -----------")
            print(resposta)
            print("-----------------------------------------------")
            
    except Exception as e:
        print(f"\n[ERRO]: Falha ao conectar com o serviço de IA: {e}")

if __name__ == "__main__":
    testar_ia()
