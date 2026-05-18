package model;

import java.sql.Date;

public class AuditoriaImpacto {
    private Long id;
    private Long projetoSocialId;
    private Double valorInvestido;
    private Double valorRetornoSocial;
    private Double indiceSroi;
    private String caminhoRecibos;
    private String caminhoFotos;
    private String dadosBrutosTexto;
    private String analiseIa;
    private String statusAuditoria; // APROVADO, EM_ANALISE, REJEITADO
    private Date dataAuditoria;

    public AuditoriaImpacto() {}

    public AuditoriaImpacto(Long id, Long projetoSocialId, Double valorInvestido, Double valorRetornoSocial, 
                            Double indiceSroi, String caminhoRecibos, String caminhoFotos, String dadosBrutosTexto, 
                            String analiseIa, String statusAuditoria, Date dataAuditoria) {
        this.id = id;
        this.projetoSocialId = projetoSocialId;
        this.valorInvestido = valorInvestido;
        this.valorRetornoSocial = valorRetornoSocial;
        this.indiceSroi = indiceSroi;
        this.caminhoRecibos = caminhoRecibos;
        this.caminhoFotos = caminhoFotos;
        this.dadosBrutosTexto = dadosBrutosTexto;
        this.analiseIa = analiseIa;
        this.statusAuditoria = statusAuditoria;
        this.dataAuditoria = dataAuditoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjetoSocialId() {
        return projetoSocialId;
    }

    public void setProjetoSocialId(Long projetoSocialId) {
        this.projetoSocialId = projetoSocialId;
    }

    public Double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(Double valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public Double getValorRetornoSocial() {
        return valorRetornoSocial;
    }

    public void setValorRetornoSocial(Double valorRetornoSocial) {
        this.valorRetornoSocial = valorRetornoSocial;
    }

    public Double getIndiceSroi() {
        return indiceSroi;
    }

    public void setIndiceSroi(Double indiceSroi) {
        this.indiceSroi = indiceSroi;
    }

    public String getCaminhoRecibos() {
        return caminhoRecibos;
    }

    public void setCaminhoRecibos(String caminhoRecibos) {
        this.caminhoRecibos = caminhoRecibos;
    }

    public String getCaminhoFotos() {
        return caminhoFotos;
    }

    public void setCaminhoFotos(String caminhoFotos) {
        this.caminhoFotos = caminhoFotos;
    }

    public String getDadosBrutosTexto() {
        return dadosBrutosTexto;
    }

    public void setDadosBrutosTexto(String dadosBrutosTexto) {
        this.dadosBrutosTexto = dadosBrutosTexto;
    }

    public String getAnaliseIa() {
        return analiseIa;
    }

    public void setAnaliseIa(String analiseIa) {
        this.analiseIa = analiseIa;
    }

    public String getStatusAuditoria() {
        return statusAuditoria;
    }

    public void setStatusAuditoria(String statusAuditoria) {
        this.statusAuditoria = statusAuditoria;
    }

    public Date getDataAuditoria() {
        return dataAuditoria;
    }

    public void setDataAuditoria(Date dataAuditoria) {
        this.dataAuditoria = dataAuditoria;
    }

    public String toJson() {
        return String.format(
                "{\"id\":%s,\"projetoSocialId\":%s,\"valorInvestido\":%.2f,\"valorRetornoSocial\":%.2f,\"indiceSroi\":%.2f,\"caminhoRecibos\":\"%s\",\"caminhoFotos\":\"%s\",\"dadosBrutosTexto\":\"%s\",\"analiseIa\":\"%s\",\"statusAuditoria\":\"%s\",\"dataAuditoria\":\"%s\"}",
                id != null ? id.toString() : "null",
                projetoSocialId != null ? projetoSocialId.toString() : "null",
                valorInvestido != null ? valorInvestido : 0.0,
                valorRetornoSocial != null ? valorRetornoSocial : 0.0,
                indiceSroi != null ? indiceSroi : 0.0,
                caminhoRecibos != null ? caminhoRecibos.replace("\"", "\\\"") : "",
                caminhoFotos != null ? caminhoFotos.replace("\"", "\\\"") : "",
                dadosBrutosTexto != null ? dadosBrutosTexto.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "") : "",
                analiseIa != null ? analiseIa.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "") : "",
                statusAuditoria != null ? statusAuditoria.replace("\"", "\\\"") : "EM_ANALISE",
                dataAuditoria != null ? dataAuditoria.toString() : ""
        );
    }
}
