package br.com.oficina.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void removerPernoite(int indice) {
        pernoites.remove(indice);
    }

    public void atualizarPernoite(int indice, Pernoite nova) {
        pernoites.set(indice, nova);
    }


    public void listarPernoites() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (pernoites.isEmpty()) {
            System.out.println("Nenhuma pernoite cadastrada.");
            return;
        }

        for (int i = 0; i < pernoites.size(); i++) {
            Pernoite p = pernoites.get(i);
            System.out.println(i + " - " + p.getLocalidade()
                    + " | " + p.getDataSaida().format(fmt)
                    + " até " + p.getDataRetorno().format(fmt)
                    + " | R$ " + String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", p.calcularValorTotal()));
        }
    }

    public String gerarRelatorio(int mesAtual, int anoAtual) {
        double total = 0;

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        sb.append("------ RELATÓRIO DE CONFORMIDADE DE PERNOITES ------\n");
        sb.append("Eu, ").append(this.nome).append(", portador do CPF ").append(this.cpf).append(",\n");
        sb.append("declaro estar ciente e de acordo com o recebimento dos valores\n");
        sb.append("referentes às pernoites realizadas conforme detalhamento abaixo:\n");
        sb.append("----------------------------------------------------\n");

        for (Pernoite p : pernoites) {

            if (p.getDataSaida().getMonthValue() == mesAtual &&
                    p.getDataSaida().getYear() == anoAtual) {


                sb.append("Viagem para: ").append(p.getLocalidade()).append("\n");
                sb.append("Periodo: ").append(p.getDataSaida().format(fmt))
                        .append(" até ").append(p.getDataRetorno().format(fmt)).append("\n");
                sb.append("Total de Noites: ").append(p.calcularQuantidadeNoites()).append("\n");
                sb.append("Valor desta viagem: R$ ").append(String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", p.calcularValorTotal())).append("\n");
                sb.append("Comprovantes: ").append(p.getComprovante()).append("\n");
                sb.append("----------------------------------------------------\n");

                total += p.calcularValorTotal();
            }
        }

        sb.append("VALOR TOTAL A RECEBER: R$ ").append(String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", total)).append("\n");
        sb.append("----------------------------------------------------\n");
        sb.append("Assinatura do Técnico: ___________________________\n");

        return sb.toString();
    }

}
