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

    public double calcularTotalDoMes(int mes, int ano) {
        double total = 0;
        for (Pernoite p : pernoites) {
            if (p.getDataSaida().getMonthValue() == mes && p.getDataSaida().getYear() == ano) {
                total += p.calcularValorTotal();
            }
        }
        return total;
    }

    public int contarPernoitesDoMes(int mes, int ano) {
        int contador = 0;
        for (Pernoite p : pernoites) {
            if (p.getDataSaida().getMonthValue() == mes && p.getDataSaida().getYear() == ano) {
                contador++;
            }
        }
        return contador;
    }

    public String gerarRelatorio(int mesAtual, int anoAtual) {

        double total = 0;
        int totalPernoites = 0;

        StringBuilder dias = new StringBuilder();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd");

        for (Pernoite p : pernoites) {
            if (p.getDataSaida().getMonthValue() == mesAtual &&
                    p.getDataSaida().getYear() == anoAtual) {

                long noites = p.calcularQuantidadeNoites();
                totalPernoites += noites;
                total += p.calcularValorTotal();

                dias.append(p.getDataSaida().format(fmt))
                        .append(" a ")
                        .append(p.getDataRetorno().format(fmt))
                        .append(" ; ");
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Curitiba, ")
                .append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM yyyy")))
                .append(".\n\n");

        sb.append("RECIBO DIÁRIAS DE VIAGENS\n\n");

        sb.append("Eu ").append(this.nome)
                .append(" inscrito no CPF: ").append(this.cpf)
                .append(" recebi da Teixpac Service Ltda situada na Rua João Reffo, 470 - Santa Felicidade, Curitiba - PR, 82410-000 ")
                .append("inscrito no CNPJ sob o nº 35.644.378/0001-02, a quantia de R$ ")
                .append(String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", total))
                .append(" referente a ")
                .append(totalPernoites)
                .append(" pernoites realizadas nos dias: ")
                .append(dias.toString())
                .append("\n\n");

        sb.append("____________________________________\n");
        sb.append(this.nome).append("\n");
        sb.append("Rua João Reffo, 470 - Santa Felicidade, Curitiba - PR\n");

        return sb.toString();
    }
}
