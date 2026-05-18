package dto;

import java.sql.Date;

public class DetalhesAuditoriaImpactoDto {
    private Long id;
    private Long projetoSocialId;
    private Double valorInvestido;
    private Double valorRetornoSocial;
    private Double indiceSroi;
    private String caminhoRecibos;
    private String caminhoFotos;
    private String dadosBrutosTexto;
    private String analiseIa;
    private String statusAuditoria;
    private Date dataAuditoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProjetoSocialId() { return projetoSocialId; }
    public void setProjetoSocialId(Long projetoSocialId) { this.projetoSocialId = projetoSocialId; }

    public Double getValorInvestido() { return valorInvestido; }
    public void setValorInvestido(Double valorInvestido) { this.valorInvestido = valorInvestido; }

    public Double getValorRetornoSocial() { return valorRetornoSocial; }
    public void setValorRetornoSocial(Double valorRetornoSocial) { this.valorRetornoSocial = valorRetornoSocial; }

    public Double getIndiceSroi() { return indiceSroi; }
    public void setIndiceSroi(Double indiceSroi) { this.indiceSroi = indiceSroi; }

    public String getCaminhoRecibos() { return caminhoRecibos; }
    public void setCaminhoRecibos(String caminhoRecibos) { this.caminhoRecibos = caminhoRecibos; }

    public String getCaminhoFotos() { return caminhoFotos; }
    public void setCaminhoFotos(String caminhoFotos) { this.caminhoFotos = caminhoFotos; }

    public String getDadosBrutosTexto() { return dadosBrutosTexto; }
    public void setDadosBrutosTexto(String dadosBrutosTexto) { this.dadosBrutosTexto = dadosBrutosTexto; }

    public String getAnaliseIa() { return analiseIa; }
    public void setAnaliseIa(String analiseIa) { this.analiseIa = analiseIa; }

    public String getStatusAuditoria() { return statusAuditoria; }
    public void setStatusAuditoria(String statusAuditoria) { this.statusAuditoria = statusAuditoria; }

    public Date getDataAuditoria() { return dataAuditoria; }
    public void setDataAuditoria(Date dataAuditoria) { this.dataAuditoria = dataAuditoria; }
}
