import java.io.*; 
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

static final String ARQUIVO_ALUNOS = "alunos.txt";
static final String ARQUIVO_DISCIPLINAS = "disciplinas.txt";

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

public static void salvarDisciplinasTxt(List<Disciplina> lista) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_DISCIPLINAS))) {
        for (Disciplina d : lista) {
            pw.println(d.getNome() + "," + d.getCodigo() + "," + d.getCargaHoraria() + "," + d.getPreReq());
        }
        System.out.println("Disciplinas salvas com sucesso em disciplinas.txt");
    } catch (IOException e) {
        System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
    }
}

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Aluno> listaAlun = carregarLista();
    List<Disciplina> listDisc = new ArrayList<>();

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
                        if (listDisc.isEmpty()) {
                            System.out.println("Nenhuma disciplina disponível.");
                        } else {
                            for (int i = 0; i < listDisc.size(); i++) {
                                System.out.println((i + 1) + " - " + listDisc.get(i));
                            }
                            System.out.println("Escolha a disciplina:");
                            int esco = scanner.nextInt();
                            scanner.nextLine();
                            if (esco >= 1 && esco <= listDisc.size()) {
                                Disciplina d = listDisc.get(esco - 1);
                                boolean pode = true;
                                for (String pr : d.getPreReq()) {
                                    if (!aluno.possuiDisciplina(pr)) {
                                        pode = false;
                                    }
                                }
                                if (!pode) {
                                    System.out.println("Faltam pré-requisitos.");
                                } else if (!d.temVaga()) {
                                    System.out.println("Sem vagas.");
                                } else {
                                    if (d.matricular(aluno)) {
                                        aluno.adicionarDisciplina(d.getNome());
                                        System.out.println("Matriculado com sucesso.");
                                    } else {
                                        System.out.println("Erro ao matricular.");
                                    }
                                }
                            } else {
                                System.out.println("Escolha inválida.");
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
                    salvarDisciplinasTxt(listDisc);
                    System.out.println("Disciplina cadastrada com sucesso.");
                } else if (opc3 == 2) {
                    if (listDisc.isEmpty()) {
                        System.out.println("Nenhuma disciplina cadastrada.");
                    } else {
                        for (Disciplina d : listDisc) {
                            System.out.println(d);
                        }
                    }
                } else {
                    System.out.println("Opção inválida.");
                }
            }

        } else if (opc == 3) {
            System.out.println("Modo avaliação/frequência ainda não implementado.");
        } else if (opc != 0) {
            System.out.println("Opção inválida.");
        }
    }

    salvarLista(listaAlun);
    scanner.close();
}
}
