package br.com.oficina.modelo;

import java.util.ArrayList;

public class Tecnico {

    private String nome;
    private String cpf;
    private ArrayList<Pernoite> pernoites;

    public Tecnico(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.pernoites = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public ArrayList<Pernoite> getPernoites() {
        return pernoites;
    }

    public void setPernoites(ArrayList<Pernoite> pernoites) {
        this.pernoites = pernoites;
    }

    public void adicionarPernoite(Pernoite p) {
        pernoites.add(p);
    }

    public double calcularTotalMensal() {
        double total = 0;
        for (Pernoite p : pernoites) {
            total += p.calcularValorTotal();
        }
        return total;
    }

    public void exibirRelatorioCompleto() {
        System.out.println("------ RELATÓRIO DE CONFORMIDADE DE PERNOITES ------");
        System.out.println("Eu, " + this.nome + ", portador do CPF " + this.cpf + ",");
        System.out.println("declaro estar ciente e de acordo com o recebimento dos valores");
        System.out.println("referentes às pernoites realizadas conforme detalhamento abaixo:");
        System.out.println("----------------------------------------------------");

        for (Pernoite p : pernoites) {
            System.out.println("Viagem para: " + p.getLocalidade());
            System.out.println("Periodo: " + p.getDataSaida() + " até " + p.getDataRetorno());
            System.out.println("Total de Noites: " + p.calcularQuantidadeNoites());
            System.out.printf("Valor desta viagem: R$ %.2f%n", p.calcularValorTotal());
            System.out.println("Comprovantes: " + p.getComprovante());
            System.out.println("----------------------------------------------------");
        }

        System.out.printf("VALOR TOTAL A RECEBER: R$ %.2f%n", this.calcularTotalMensal());
        System.out.println("----------------------------------------------------");
        System.out.println("Assinatura do Técnico: ___________________________");
    }



}
