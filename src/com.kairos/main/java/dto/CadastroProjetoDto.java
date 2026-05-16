package dto;

public class CadastroProjetoDto {
    private String titulo;
    private String relato;
    private String orcamento;
    private String status;
    private Long ongId;

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

    public String getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(String orcamento) {
        this.orcamento = orcamento;
    }

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
