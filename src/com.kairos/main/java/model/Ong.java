package model;

public class Ong {
    private Long id;
    private String nome;
    private String descricao;
    private String areaAtuacao;
    private String cnpj;

    public Ong() {}
    public Ong(Long id, String nome, String descricao, String areaAtuacao, String cnpj) {
        this.id = id; this.nome = nome; this.descricao = descricao;
        this.areaAtuacao = areaAtuacao; this.cnpj = cnpj;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getAreaAtuacao() { return areaAtuacao; }
    public void setAreaAtuacao(String areaAtuacao) { this.areaAtuacao = areaAtuacao; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String toJson() {
        return String.format(
                "{\"id\":%d,\"nome\":\"%s\",\"descricao\":\"%s\",\"areaAtuacao\":\"%s\",\"cnpj\":\"%s\"}",
                id, nome, descricao, areaAtuacao, cnpj);
    }
}