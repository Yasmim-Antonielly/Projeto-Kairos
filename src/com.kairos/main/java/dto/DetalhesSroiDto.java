package dto;

public class DetalhesSroiDto {
    private Long id;
    private Long projetoId;
    private Double valorInvestido;
    private Double valorImpactoSocial;
    private Double indiceSroi;
    private String analiseIa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProjetoId() { return projetoId; }
    public void setProjetoId(Long projetoId) { this.projetoId = projetoId; }
    public Double getValorInvestido() { return valorInvestido; }
    public void setValorInvestido(Double valorInvestido) { this.valorInvestido = valorInvestido; }
    public Double getValorImpactoSocial() { return valorImpactoSocial; }
    public void setValorImpactoSocial(Double valorImpactoSocial) { this.valorImpactoSocial = valorImpactoSocial; }
    public Double getIndiceSroi() { return indiceSroi; }
    public void setIndiceSroi(Double indiceSroi) { this.indiceSroi = indiceSroi; }
    public String getAnaliseIa() { return analiseIa; }
    public void setAnaliseIa(String analiseIa) { this.analiseIa = analiseIa; }
}
