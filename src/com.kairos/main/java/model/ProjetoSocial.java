package model;

public class ProjetoSocial {
    private Long id;
    private String titulo;
    private String descricao;
    private String categoriaEsg; // AMBIENTAL, SOCIAL, GOVERNANCA
    private Double orcamentoNecessario;
    private String ongResponsavel;
    private String cnpj;
    private String metasComunidade;

    public ProjetoSocial() {}

    public ProjetoSocial(Long id, String titulo, String descricao, String categoriaEsg, 
                         Double orcamentoNecessario, String ongResponsavel, String cnpj, String metasComunidade) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoriaEsg = categoriaEsg;
        this.orcamentoNecessario = orcamentoNecessario;
        this.ongResponsavel = ongResponsavel;
        this.cnpj = cnpj;
        this.metasComunidade = metasComunidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoriaEsg() {
        return categoriaEsg;
    }

    public void setCategoriaEsg(String categoriaEsg) {
        this.categoriaEsg = categoriaEsg;
    }

    public Double getOrcamentoNecessario() {
        return orcamentoNecessario;
    }

    public void setOrcamentoNecessario(Double orcamentoNecessario) {
        this.orcamentoNecessario = orcamentoNecessario;
    }

    public String getOngResponsavel() {
        return ongResponsavel;
    }

    public void setOngResponsavel(String ongResponsavel) {
        this.ongResponsavel = ongResponsavel;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getMetasComunidade() {
        return metasComunidade;
    }

    public void setMetasComunidade(String metasComunidade) {
        this.metasComunidade = metasComunidade;
    }

    public String toJson() {
        return String.format(
                "{\"id\":%d,\"titulo\":\"%s\",\"descricao\":\"%s\",\"categoriaEsg\":\"%s\",\"orcamentoNecessario\":%.2f,\"ongResponsavel\":\"%s\",\"cnpj\":\"%s\",\"metasComunidade\":\"%s\"}",
                id, 
                titulo != null ? titulo.replace("\"", "\\\"") : "", 
                descricao != null ? descricao.replace("\"", "\\\"") : "", 
                categoriaEsg != null ? categoriaEsg.replace("\"", "\\\"") : "", 
                orcamentoNecessario != null ? orcamentoNecessario : 0.0, 
                ongResponsavel != null ? ongResponsavel.replace("\"", "\\\"") : "", 
                cnpj != null ? cnpj.replace("\"", "\\\"") : "", 
                metasComunidade != null ? metasComunidade.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "") : ""
        );
    }
}
