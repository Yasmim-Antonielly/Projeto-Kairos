package dto;

public class CadastroAuditoriaImpactoDto {
    private Long projetoSocialId;
    private Double valorInvestido;
    private String caminhoRecibos;
    private String caminhoFotos;
    private String dadosBrutosTexto;

    public Long getProjetoSocialId() { return projetoSocialId; }
    public void setProjetoSocialId(Long projetoSocialId) { this.projetoSocialId = projetoSocialId; }

    public Double getValorInvestido() { return valorInvestido; }
    public void setValorInvestido(Double valorInvestido) { this.valorInvestido = valorInvestido; }

    public String getCaminhoRecibos() { return caminhoRecibos; }
    public void setCaminhoRecibos(String caminhoRecibos) { this.caminhoRecibos = caminhoRecibos; }

    public String getCaminhoFotos() { return caminhoFotos; }
    public void setCaminhoFotos(String caminhoFotos) { this.caminhoFotos = caminhoFotos; }

    public String getDadosBrutosTexto() { return dadosBrutosTexto; }
    public void setDadosBrutosTexto(String dadosBrutosTexto) { this.dadosBrutosTexto = dadosBrutosTexto; }
}
