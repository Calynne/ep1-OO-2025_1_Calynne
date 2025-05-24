import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    static final String ARQUIVO = "alunos.dat";

    public static void salvarLista(List<Aluno> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }

    public static List<Aluno> carregarLista() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (List<Aluno>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // NOVO MÉTODO PARA SALVAR COMO TXT
    public static void salvarListaComoTxt(List<Aluno> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("alunos.txt"))) {
            for (Aluno aluno : lista) {
                pw.println(aluno.getNome() + "," + aluno.getMatricula() + "," + aluno.getCurso());
            }
            System.out.println("Alunos salvos no arquivo alunos.txt com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos no formato texto: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Aluno> listaAlun = carregarLista();

        List<Disciplina> listDisc = new ArrayList<>();
        List<String> prereq1 = new ArrayList<>();
        List<String> prereq2 = new ArrayList<>();
        prereq2.add("Cálculo 1");

        listDisc.add(new Disciplina("Cálculo 1", 3, prereq1));
        listDisc.add(new Disciplina("Estruturas de Dados", 2, prereq2));

        System.out.println("Digite 1 para acessar o Modo aluno (Normal e Especial)");
        System.out.println("Digite 2 para acessar o Modo disciplina/turma");
        System.out.println("Digite 3 para acessar o Modo avaliação/frequência");

        int opc = scanner.nextInt();
        scanner.nextLine();

        while (opc == 0) {
            System.out.println("Digite 1 para acessar o Modo aluno (Normal e Especial)");
            System.out.println("Digite 2 para acessar o Modo disciplina/turma");
            System.out.println("Digite 3 para acessar o Modo avaliação/frequência");
            opc = scanner.nextInt();
            scanner.nextLine();
        }

        if (opc == 1) {
            System.out.println("Caro aluno (a). Seja bem vindo! Escolha uma das opções pra dar continuidade ao que você deseja:");
            System.out.println("Digite 1 pra se cadastrar ou editar seu cadastro");
            System.out.println("Digite 2 pra listar os alunos cadastrados");
            System.out.println("Digite 3 pra se matricular em uma disciplina");
            System.out.println("Digite 4 pra trancar uma disciplina ou semestre");
            System.out.println("0 - Voltar");

            int opc2 = scanner.nextInt();
            scanner.nextLine();

            while (opc2 != 0) {
                if (opc2 == 1) {
                    System.out.println("Você escolheu a opção 1");
                    System.out.println("Digite o seu nome:");
                    String nome = scanner.nextLine();
                    System.out.println("Digite a sua matricula:");
                    String matricula = scanner.nextLine();
                    System.out.println("Digite o seu curso de graduação:");
                    String curso = scanner.nextLine();

                    Aluno aluno = new Aluno(nome, matricula, curso);
                    listaAlun.add(aluno);
                    System.out.println("Cadastro realizado com sucesso!");

                    salvarListaComoTxt(listaAlun);

                } else if (opc2 == 2) {
                    if (listaAlun.isEmpty()) {
                        System.out.println("Nesta lista não tem nenhum aluno cadastrado.");
                    } else {
                        System.out.println("Alunos cadastrados:");
                        for (int i = 0; i < listaAlun.size(); i++) {
                            System.out.println((i + 1) + ". " + listaAlun.get(i));
                        }
                    }

                } else if (opc2 == 3) {
                    System.out.println("Digite sua matrícula:");
                    String matriculaBusca = scanner.nextLine();

                    Aluno alunoMat = null;
                    for (Aluno a : listaAlun) {
                        if (a.getMatricula().equals(matriculaBusca)) {
                            alunoMat = a;
                            break;
                        }
                    }

                    if (alunoMat == null) {
                        System.out.println("Aluno não encontrado.");
                        continue;
                    }

                    System.out.println("Disciplinas disponíveis:");
                    for (int i = 0; i < listDisc.size(); i++) {
                        System.out.println((i + 1) + " - " + listDisc.get(i));
                    }

                    System.out.println("Digite o número da disciplina pra se matricular:");
                    int escolha = scanner.nextInt();
                    scanner.nextLine();

                    if (escolha < 1 || escolha > listDisc.size()) {
                        System.out.println("Opção inválida.");
                        continue;
                    }

                    Disciplina d = listDisc.get(escolha - 1);

                    boolean temTodosPreReq = true;
                    for (String pr : d.getPreReq()) {
                        if (!alunoMat.possuiDisciplina(pr)) {
                            temTodosPreReq = false;
                            break;
                        }
                    }

                    if (!temTodosPreReq) {
                        System.out.println("Você não possui os pré-requisitos pra essa disciplina.");
                        continue;
                    }

                    if (!d.temVaga()) {
                        System.out.println("Não tem vaga disponível nessa disciplina.");
                        continue;
                    }

                    if (d.matricular(alunoMat)) {
                        alunoMat.adicionarDisciplina(d.getNome());
                        System.out.println("Matrícula feita com sucesso!");
                    } else {
                        System.out.println("Erro ao realizar matrícula.");
                    }

                } else if (opc2 == 4) {
                    System.out.println("Função de trancamento ainda não implementada.");
                } else {
                    System.out.println("Opção inválida. Tenta de novo.");
                }
                System.out.println("\nEscolha uma das opções para dar continuidade ao que você deseja:");
                System.out.println("Digite 1 pra se cadastrar ou editar");
                System.out.println("Digite 2 pra listar alunos");
                System.out.println("Digite 3 pra se matricular em disciplina");
                System.out.println("Digite 4 pra trancar disciplina ou semestre");
                System.out.println("Digite 0 pra voltar");
                opc2 = scanner.nextInt();
                scanner.nextLine();
            }

        } else if (opc == 2) {
            System.out.println("Modo disciplina/turma ainda não tá pronto.");
        } else if (opc == 3) {
            System.out.println("Modo avaliação/frequência ainda não tá pronto.");
        } else {
            System.out.println("Opção inválida.");
        }

       salvarListaComoTxt(listaAlun);
        scanner.close();
    }
}
