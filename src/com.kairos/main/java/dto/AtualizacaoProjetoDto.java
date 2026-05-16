package dto;

public class AtualizacaoProjetoDto {
    private String id;
    private String titulo;
    private String relato;
    private double orcamento;
    private String status;
    private Long ongId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRelato() {
        return relato;
    }

    public void setRelato(String relato) {
        this.relato = relato;
    }

    public Double getOrcamento() { return orcamento; }
    public void setOrcamento(Double orcamento) { this.orcamento = orcamento; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOngId() {
        return ongId;
    }

    public void setOngId(Long ongId) {
        this.ongId = ongId;
    }
}
