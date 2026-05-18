package model;

public class Investidor {
    private Long id;
    private String nome;
    private String tipoInvestidor; // ANJO, FUNDO, CORPORATIVO
    private String teseInvestimento;
    private String criteriosEsg;
    private Double orcamentoDisponivel;
    private String cnpjCpf;

    public Investidor() {}

    public Investidor(Long id, String nome, String tipoInvestidor, String teseInvestimento, 
                      String criteriosEsg, Double orcamentoDisponivel, String cnpjCpf) {
        this.id = id;
        this.nome = nome;
        this.tipoInvestidor = tipoInvestidor;
        this.teseInvestimento = teseInvestimento;
        this.criteriosEsg = criteriosEsg;
        this.orcamentoDisponivel = orcamentoDisponivel;
        this.cnpjCpf = cnpjCpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoInvestidor() {
        return tipoInvestidor;
    }

    public void setTipoInvestidor(String tipoInvestidor) {
        this.tipoInvestidor = tipoInvestidor;
    }

    public String getTeseInvestimento() {
        return teseInvestimento;
    }

    public void setTeseInvestimento(String teseInvestimento) {
        this.teseInvestimento = teseInvestimento;
    }

    public String getCriteriosEsg() {
        return criteriosEsg;
    }

    public void setCriteriosEsg(String criteriosEsg) {
        this.criteriosEsg = criteriosEsg;
    }

    public Double getOrcamentoDisponivel() {
        return orcamentoDisponivel;
    }

    public void setOrcamentoDisponivel(Double orcamentoDisponivel) {
        this.orcamentoDisponivel = orcamentoDisponivel;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String toJson() {
        return String.format(
                "{\"id\":%d,\"nome\":\"%s\",\"tipoInvestidor\":\"%s\",\"teseInvestimento\":\"%s\",\"criteriosEsg\":\"%s\",\"orcamentoDisponivel\":%.2f,\"cnpjCpf\":\"%s\"}",
                id,
                nome != null ? nome.replace("\"", "\\\"") : "",
                tipoInvestidor != null ? tipoInvestidor.replace("\"", "\\\"") : "",
                teseInvestimento != null ? teseInvestimento.replace("\"", "\\\"") : "",
                criteriosEsg != null ? criteriosEsg.replace("\"", "\\\"") : "",
                orcamentoDisponivel != null ? orcamentoDisponivel : 0.0,
                cnpjCpf != null ? cnpjCpf.replace("\"", "\\\"") : ""
        );
    }
}
