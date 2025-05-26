import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    static final String ARQUIVO_ALUNOS = "alunos.txt";
    static final String ARQUIVO_DISCIPLINAS = "disciplinas.txt";
    static final String ARQUIVO_TURMAS = "turmas.txt";

    public static void salvarLista(List<Aluno> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_ALUNOS))) {
            for (Aluno a : lista) {
                String linha = a.getNome() + ";" + a.getMatricula() + ";" + a.getCurso() + ";" + a.isEspecial();
                List<String> disciplinas = a.getDisciplinasMatriculadas();
                for (String d : disciplinas) {
                    linha += ";" + d;
                }
                pw.println(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }

    public static void salvarDisciplinas(List<Disciplina> disciplinas) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_DISCIPLINAS))) {
        for (Disciplina d : disciplinas) {
            StringBuilder linha = new StringBuilder();
            linha.append(d.getNome()).append(";")
                 .append(d.getCodigo()).append(";")
                 .append(d.getCargaHoraria());

            for (String prereq : d.getPreRequisitos()) {
                linha.append(";").append(prereq);
            }

            pw.println(linha);
        }
    } catch (IOException e) {
        System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
    }
}

public static void salvarTurmas(List<Disciplina> disciplinas) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_TURMAS))) {
        for (Disciplina d : disciplinas) {
            for (Turma t : d.getTurmas()) {
                String linha = d.getCodigo() + ";" + t.getProfessor() + ";" + t.getSemestre() + ";" +
                        t.getAvaliacao() + ";" + t.isPresencial() + ";" + t.getSala() + ";" +
                        t.getHorario() + ";" + t.getCapacidadeMaxima();
                pw.println(linha);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao salvar turmas: " + e.getMessage());
    }
}
public static List<Disciplina> carregarDisciplinas() {
    List<Disciplina> disciplinas = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_DISCIPLINAS))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length >= 3) {
                String nome = partes[0];
                String codigo = partes[1];
                int carga = Integer.parseInt(partes[2]);
                List<String> prereqs = new ArrayList<>();
                for (int i = 3; i < partes.length; i++) {
                    prereqs.add(partes[i]);
                }
                disciplinas.add(new Disciplina(nome, codigo, carga, prereqs));
            }
        }
    } catch (IOException e) {
        System.out.println("Arquivo de disciplinas não encontrado, iniciando lista vazia.");
    }
    return disciplinas;
}

public static void carregarTurmas(List<Disciplina> disciplinas) {
    try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_TURMAS))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length >= 8) {
                String codigoDisciplina = partes[0];
                String prof = partes[1];
                String semestre = partes[2];
                String avaliacao = partes[3];
                boolean presencial = Boolean.parseBoolean(partes[4]);
                String sala = partes[5];
                String horario = partes[6];
                int capacidade = Integer.parseInt(partes[7]);

                Turma turma = new Turma(prof, semestre, avaliacao, presencial, sala, horario, capacidade);

                for (Disciplina d : disciplinas) {
                    if (d.getCodigo().equals(codigoDisciplina)) {
                        d.adicionarTurma(turma);
                        break;
                    }
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Arquivo de turmas não encontrado, iniciando sem turmas.");
    }
}


    public static List<Aluno> carregarLista() {
        List<Aluno> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_ALUNOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    String nome = partes[0];
                    String matricula = partes[1];
                    String curso = partes[2];
                    boolean especial = Boolean.parseBoolean(partes[3]);
                    Aluno a = new Aluno(nome, matricula, curso, especial);
                    List<String> disciplinas = new ArrayList<>();
                    for (int i = 4; i < partes.length; i++) {
                        disciplinas.add(partes[i]);
                    }
                    a.setDisciplinasMatriculadas(disciplinas);
                    lista.add(a);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de alunos não encontrado, iniciando lista vazia.");
        }
        return lista;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Aluno> listaAlun = carregarLista();
    
        List<Disciplina> listDisc = carregarDisciplinas();
            carregarTurmas(listDisc);

        int opc = -1;
        while (opc != 0) {
            System.out.println("Digite 1 para acessar o Modo aluno (Normal e Especial)");
            System.out.println("Digite 2 para acessar o Modo disciplina/turma");
            System.out.println("Digite 3 para acessar o Modo avaliação/frequência");
            System.out.println("Digite 0 para sair");
            opc = scanner.nextInt();
            scanner.nextLine();

            if (opc == 1) {
                int opc2 = -1;
                while (opc2 != 0) {
                    System.out.println("Caro aluno (a). Seja bem vindo! Escolha uma das opções pra dar continuidade:");
                    System.out.println("1 - Cadastrar/editar");
                    System.out.println("2 - Listar alunos");
                    System.out.println("3 - Matricular em disciplina");
                    System.out.println("4 - Trancar disciplina");
                    System.out.println("0 - Voltar");

                    opc2 = scanner.nextInt();
                    scanner.nextLine();

                    if (opc2 == 1) {
                        System.out.println("Digite o seu nome:");
                        String nome = scanner.nextLine();
                        System.out.println("Digite a sua matrícula:");
                        String matricula = scanner.nextLine();
                        System.out.println("Digite o seu curso:");
                        String curso = scanner.nextLine();
                        System.out.println("Você é aluno especial? (V/F):");
                        String especialStr = scanner.nextLine();
                        boolean especial = especialStr.equalsIgnoreCase("V");
                        boolean existe = false;
                        for (Aluno a : listaAlun) {
                            if (a.getMatricula().equals(matricula)) {
                                existe = true;
                                break;
                            }
                        }
                        if (existe) {
                            System.out.println("Já tem um aluno com essa matrícula.");
                        } else {
                            Aluno novo = new Aluno(nome, matricula, curso, especial);
                            listaAlun.add(novo);
                            System.out.println("Aluno cadastrado com sucesso!");
                            salvarLista(listaAlun);
                        }
                    } else if (opc2 == 2) {
                        if (listaAlun.isEmpty()) {
                            System.out.println("Nenhum aluno cadastrado.");
                        } else {
                            for (Aluno a : listaAlun) {
                                System.out.println(a);
                            }
                        }
                    } else if (opc2 == 3) {
                        System.out.println("Digite sua matrícula:");
                        String mat = scanner.nextLine();
                        Aluno aluno = null;
                        for (Aluno a : listaAlun) {
                            if (a.getMatricula().equals(mat)) {
                                aluno = a;
                                break;
                            }
                        }
                        if (aluno == null) {
                            System.out.println("Aluno não encontrado.");
                        } else {
                            for (int i = 0; i < listDisc.size(); i++) {
                                System.out.println((i + 1) + " - " + listDisc.get(i).getNome());
                            }
                            System.out.println("Escolha a disciplina:");
                            int esco = scanner.nextInt();
                            scanner.nextLine();
                            if (esco >= 1 && esco <= listDisc.size()) {
                                Disciplina disc = listDisc.get(esco - 1);
                                List<Turma> turmas = disc.getTurmas();
                                if (turmas.isEmpty()) {
                                    System.out.println("Nenhuma turma disponível para essa disciplina.");
                                } else {
                                    for (int j = 0; j < turmas.size(); j++) {
                                        System.out.println((j + 1) + " - " + turmas.get(j));
                                    }
                                    System.out.println("Escolha a turma:");
                                    int escTurma = scanner.nextInt();
                                    scanner.nextLine();
                                    if (escTurma >= 1 && escTurma <= turmas.size()) {
                                        Turma turma = turmas.get(escTurma - 1);
                                        if (!turma.temVaga()) {
                                            System.out.println("Turma sem vagas disponíveis.");
                                        } else {
                                            
                                            turma.matricular(aluno);
                                            aluno.adicionarDisciplina(disc.getNome());
                                            System.out.println("Aluno matriculado com sucesso na turma.");
                                        }
                                    }
                                }
                            }
                        }
                    } else if (opc2 == 4) {
                        System.out.println("Digite sua matrícula:");
                        String mat = scanner.nextLine();
                        Aluno aluno = null;
                        for (Aluno a : listaAlun) {
                            if (a.getMatricula().equals(mat)) {
                                aluno = a;
                                break;
                            }
                        }
                        if (aluno == null) {
                            System.out.println("Aluno não encontrado.");
                        } else {
                            aluno.listarDisciplinas();
                            System.out.println("Digite o nome da disciplina que deseja trancar:");
                            String nome = scanner.nextLine();
                            aluno.trancarDisciplina(nome);
                            System.out.println("Disciplina trancada com sucesso.");
                            for (Disciplina d : listDisc) {
                                if (d.getNome().equalsIgnoreCase(nome)) {
                                    for (Turma t : d.getTurmas()) {
                                        t.removerAluno(aluno);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }

            } else if (opc == 2) {
                int opc3 = -1;
                while (opc3 != 0) {
                    System.out.println("Bem vindo ao modo disciplina/turma!");
                    System.out.println("1 - Cadastrar nova disciplina");
                    System.out.println("2 - Listar disciplinas");
                    System.out.println("3 - Cadastrar turma em uma disciplina");
                    System.out.println("4 - Listar alunos matriculados em cada turma");
                    System.out.println("0 - Voltar");

                    opc3 = scanner.nextInt();
                    scanner.nextLine();

                    if (opc3 == 1) {
                        System.out.println("Digite o nome da disciplina:");
                        String nome = scanner.nextLine();
                        System.out.println("Digite o código da disciplina:");
                        String codigo = scanner.nextLine();
                        System.out.println("Digite a carga horária:");
                        int carga = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Quantos pré-requisitos?");
                        int qtde = scanner.nextInt();
                        scanner.nextLine();
                        List<String> prereqs = new ArrayList<>();
                        for (int i = 0; i < qtde; i++) {
                            System.out.println("Digite o nome do pré-requisito " + (i + 1) + ":");
                            String pr = scanner.nextLine();
                            prereqs.add(pr);
                        }
                        Disciplina nova = new Disciplina(nome, codigo, carga, prereqs);
                        listDisc.add(nova);
                        System.out.println("Disciplina cadastrada com sucesso.");
                    } else if (opc3 == 2) {
                        for (Disciplina d : listDisc) {
                            System.out.println(d);
                        }
                    } else if (opc3 == 3) {
                        if (listDisc.isEmpty()) {
                            System.out.println("Nenhuma disciplina cadastrada.");
                        } else {
                            for (int i = 0; i < listDisc.size(); i++) {
                                System.out.println((i + 1) + " - " + listDisc.get(i).getNome());
                            }
                            System.out.println("Escolha a disciplina para adicionar a turma:");
                            int esc = scanner.nextInt();
                            scanner.nextLine();
                            if (esc >= 1 && esc <= listDisc.size()) {
                                Disciplina d = listDisc.get(esc - 1);
                                System.out.println("Digite o professor responsável:");
                                String prof = scanner.nextLine();
                                System.out.println("Digite o semestre:");
                                String semestre = scanner.nextLine();
                                System.out.println("Digite a forma de avaliação:");
                                String avaliacao = scanner.nextLine();
                                System.out.println("A turma é presencial? (V/F):");
                                boolean presencial = scanner.nextLine().equalsIgnoreCase("V");
                                String sala = "";
                                if (presencial) {
                                    System.out.println("Digite a sala:");
                                    sala = scanner.nextLine();
                                }
                                System.out.println("Digite o horário da turma:");
                                String horario = scanner.nextLine();
                                System.out.println("Digite a capacidade máxima:");
                                int capacidade = scanner.nextInt();
                                scanner.nextLine();
                                Turma novaTurma = new Turma(prof, semestre, avaliacao, presencial, sala, horario, capacidade);
                                d.adicionarTurma(novaTurma);
                            }
                        }
                    } else if (opc3 == 4) {
                        for (Disciplina d : listDisc) {
                            System.out.println("Disciplina: " + d.getNome());
                            for (Turma t : d.getTurmas()) {
                                System.out.println(t);
                                List<Aluno> alunos = t.getAlunosMatriculados();
                                if (alunos.isEmpty()) {
                                    System.out.println("Nenhum aluno matriculado.");
                                } else {
                                    System.out.println("Alunos:");
                                    for (Aluno a : alunos) {
                                        System.out.println("- " + a.getNome() + " (" + a.getMatricula() + ")");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        salvarLista(listaAlun);

        salvarDisciplinas(listDisc);
        salvarTurmas(listDisc);
        salvarLista(listaAlun);

        scanner.close();
    }
}
