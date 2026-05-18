package dto;

public class CadastroProjetoSocialDto {
    private String titulo;
    private String descricao;
    private String categoriaEsg;
    private Double orcamentoNecessario;
    private String ongResponsavel;
    private String cnpj;
    private String metasComunidade;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoriaEsg() { return categoriaEsg; }
    public void setCategoriaEsg(String categoriaEsg) { this.categoriaEsg = categoriaEsg; }

    public Double getOrcamentoNecessario() { return orcamentoNecessario; }
    public void setOrcamentoNecessario(Double orcamentoNecessario) { this.orcamentoNecessario = orcamentoNecessario; }

    public String getOngResponsavel() { return ongResponsavel; }
    public void setOngResponsavel(String ongResponsavel) { this.ongResponsavel = ongResponsavel; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getMetasComunidade() { return metasComunidade; }
    public void setMetasComunidade(String metasComunidade) { this.metasComunidade = metasComunidade; }
}
