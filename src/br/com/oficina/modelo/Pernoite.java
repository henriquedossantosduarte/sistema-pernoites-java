package br.com.oficina.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Pernoite {

    private LocalDate dataSaida;
    private LocalDate dataRetorno;
    private String localidade;
    private String comprovante;

    public Pernoite(LocalDate dataSaida, LocalDate dataRetorno, String localidade, String comprovante) {
        this.dataSaida = dataSaida;
        this.dataRetorno = dataRetorno;
        this.localidade = localidade;
        this.comprovante = comprovante;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public long calcularQuantidadeNoites() {
        long noites = ChronoUnit.DAYS.between(this.dataSaida, this.dataRetorno);
        return noites;
    }

    public double calcularValorTotal() {
        return calcularQuantidadeNoites() * 50.00;
    }
}
