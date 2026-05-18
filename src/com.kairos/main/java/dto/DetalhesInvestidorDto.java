package dto;

public class DetalhesInvestidorDto {
    private Long id;
    private String nome;
    private String tipoInvestidor;
    private String teseInvestimento;
    private String criteriosEsg;
    private Double orcamentoDisponivel;
    private String cnpjCpf;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipoInvestidor() { return tipoInvestidor; }
    public void setTipoInvestidor(String tipoInvestidor) { this.tipoInvestidor = tipoInvestidor; }

    public String getTeseInvestimento() { return teseInvestimento; }
    public void setTeseInvestimento(String teseInvestimento) { this.teseInvestimento = teseInvestimento; }

    public String getCriteriosEsg() { return criteriosEsg; }
    public void setCriteriosEsg(String criteriosEsg) { this.criteriosEsg = criteriosEsg; }

    public Double getOrcamentoDisponivel() { return orcamentoDisponivel; }
    public void setOrcamentoDisponivel(Double orcamentoDisponivel) { this.orcamentoDisponivel = orcamentoDisponivel; }

    public String getCnpjCpf() { return cnpjCpf; }
    public void setCnpjCpf(String cnpjCpf) { this.cnpjCpf = cnpjCpf; }
}
