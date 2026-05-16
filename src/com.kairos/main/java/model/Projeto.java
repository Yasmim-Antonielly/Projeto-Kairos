package model;

public class Projeto {
    private Long id;
    private String titulo;
    private String relato;
    private Double orcamento;
    private String status;
    private Long ongId;

    public Projeto() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getRelato() { return relato; }
    public void setRelato(String relato) { this.relato = relato; }
    public Double getOrcamento() { return orcamento; }
    public void setOrcamento(Double orcamento) { this.orcamento = orcamento; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getOngId() { return ongId; }
    public void setOngId(Long ongId) { this.ongId = ongId; }

    public String toJson() {
        return String.format(
                "{\"id\":%d,\"titulo\":\"%s\",\"orcamento\":%.2f,\"status\":\"%s\",\"ongId\":%d}",
                id, titulo, orcamento, status, ongId);
    }
}
