package br.com.oficina.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.BufferedWriter;  // Para escrever de forma eficiente (em blocos)
import java.io.FileWriter;      // Para conectar com o arquivo físico no HD
import java.io.IOException; // Para tratar erros (ex: disco cheio, arquivo protegido)

import java.io.BufferedReader;
import java.io.FileReader;
public class Principal {

    public static void main(String[] args) {

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Scanner teclado = new Scanner(System.in);



        ArrayList<Tecnico> listaTecnicos = carregarDados();
        int opcao = -1;

        do {
            System.out.println("\n========== SISTEMA DE PERNOITES ==========");
            System.out.println("1. Cadastrar Novo Técnico");
            System.out.println("2. Adicionar Nova Pernoite");
            System.out.println("3. Gerar Relatório de Conformidade");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = teclado.nextInt();
            teclado.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {
                    case 1:
                        System.out.println("\n--- CADASTRO DE TÉCNICO ---");

                        System.out.println("Digite o nome do técnico: ");
                        String nome = teclado.nextLine();
                        System.out.println("Digite o CPF do técnico: ");
                        String cpf = teclado.nextLine();
                        Tecnico novoTecnico  = new Tecnico(nome, cpf);

                        listaTecnicos.add(novoTecnico);
                        System.out.println("Técnico " + nome + " cadastrado com sucesso! ");

                        break;

                case 2:
                    System.out.println("\n--- ADICIONAR PERNOITE ---");
                    Tecnico tEscolhido = selecionarTecnico(listaTecnicos, teclado);

                    if (tEscolhido == null) {
                        System.out.println("Cadastre um técnico primeiro.");
                    } else {
                        System.out.println("Digite a sua localização Ex( nome BASE, nome HOTEL, nome CLIENTE ) ");
                        String localidade = teclado.nextLine();

                        LocalDate dataSaida = lerDataValida("Digite a data de saída (dd/mm/aaaa): ", null, teclado, formatador);
                        LocalDate dataRetorno = lerDataValida("Digite a data de retorno (dd/mm/aaaa): ", dataSaida, teclado, formatador);

                        System.out.println("Adicione um comprovante da localização OBS( FUNÇÃO EM TESTE, DIGITE APENAS SUA LOCALIZAÇÃO.");
                        String comprovante = teclado.nextLine();

                        Pernoite p = new Pernoite(dataSaida, dataRetorno,localidade,comprovante);
                        tEscolhido.adicionarPernoite(p);

                        System.out.println("Pernoite em " + localidade + " adicionada com sucesso!");
                    }

                    break;


                case 3:
                    System.out.println("\n--- GERANDO RELATÓRIO ---");
                    // Chamamos o nosso "especialista" para resolver a escolha:
                    Tecnico tRelatorio = selecionarTecnico(listaTecnicos, teclado);

                    if (tRelatorio == null) {
                        System.out.println("Cadastre um técnico primeiro.");
                    } else {
                        tRelatorio.exibirRelatorioCompleto();
                    }
                    break;

                case 0:
                    System.out.println("Saindo e salvando dados...");
                    salvarDados(listaTecnicos);
                    System.out.println("Até logo!");
                    break;
            }

        } while (opcao != 0);

        teclado.close();
    }
    // Este método é um "especialista" em escolher técnicos da lista
    public static Tecnico selecionarTecnico(ArrayList<Tecnico> lista, Scanner teclado) {
        if (lista.isEmpty()) {
            return null; // Se não tem ninguém, avisa que não encontrou
        }

        System.out.println("\n--- SELECIONE O TÉCNICO ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + " - " + lista.get(i).getNome());
        }

        Tecnico tEscolhido = null;
        while (tEscolhido == null) {
            try {
                System.out.print("Digite o número do técnico: ");
                int indice = Integer.parseInt(teclado.nextLine());

                if (indice >= 0 && indice < lista.size()) {
                    tEscolhido = lista.get(indice);
                } else {
                    System.out.println("Erro: Técnico não encontrado na lista.");
                }
            } catch (Exception e) {
                System.out.println("Erro: Digite apenas o número (ID) do técnico.");
            }
        }
        return tEscolhido;
    }

    public static LocalDate lerDataValida(String mensagem, LocalDate dataMinima, Scanner teclado, DateTimeFormatter formatador) {
        LocalDate data = null;
        while (data == null) {
            try {
                System.out.println(mensagem);
                String texto = teclado.nextLine();
                LocalDate dataTemporaria = LocalDate.parse(texto, formatador);

                if (dataTemporaria.isAfter(LocalDate.now())) {
                    System.out.println("Erro: A data não pode ser no futuro!");
                } else if (dataMinima != null && dataTemporaria.isBefore(dataMinima)) {
                    System.out.println("Erro: A data não pode ser anterior à data de saída!");
                } else {
                    data = dataTemporaria;
                }
            } catch (Exception e) {
                System.out.println("Erro: Formato inválido! use o padrão dd/mm/aaaa.");
            }
        }
        return data;
    }

    public static void salvarDados(ArrayList<Tecnico> lista) {

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("dados_sistema.txt"))) {

            for (Tecnico t : lista){

                escritor.write("t" + t.getNome() + ";" + t.getCpf());
                escritor.newLine();
            }
            System.out.println("Todos tecnicos salvo no arquivo com sucesso");
        } catch (IOException e){

            System.out.println("Erro ao acessar o arquivo: " + e.getMessage());

        }
    }

    public static ArrayList<Tecnico> carregarDados(){

        ArrayList<Tecnico> lista = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader("tecnico.txt"))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");

                String nome = partes[0];
                String cpf = partes[1];

                Tecnico t = new Tecnico(nome, cpf);

                lista.add(t);
            }
            System.out.println("Dados carregado com sucesso");
        }   catch (IOException e) {

            System.out.println("Aviso: arquivo de dados não encontrado (novo sistema).");
        }

        return lista;
    }
}