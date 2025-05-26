import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Disciplina implements Serializable {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private List<String> preRequisitos;
    private List<Turma> turmas;

    public Disciplina(String nome, String codigo, int cargaHoraria, List<String> preRequisitos) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>(preRequisitos);
        this.turmas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void adicionarTurma(Turma turma) {
        for (Turma t : turmas) {
            if (t.getHorario().equalsIgnoreCase(turma.getHorario())) {
                System.out.println("Já existe uma turma nesse horário.");
                return;
            }
        }
        turmas.add(turma);
        System.out.println("Turma adicionada com sucesso à disciplina " + nome + ".");
    }

    public String toString() {
        return "Nome: " + nome +
               ", Código: " + codigo +
               ", Carga horária: " + cargaHoraria +
               ", Pré-requisitos: " + (preRequisitos.isEmpty() ? "Nenhum" : preRequisitos.toString());
    }

    // ✅ Converte a disciplina em linha de texto para salvar em .txt
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(";")
          .append(codigo).append(";")
          .append(cargaHoraria);
        for (String prereq : preRequisitos) {
            sb.append(";").append(prereq);
        }
        return sb.toString();
    }

    // ✅ Cria disciplina a partir de uma linha do .txt
    public static Disciplina fromCSV(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 3) return null;

        String nome = partes[0];
        String codigo = partes[1];
        int cargaHoraria = Integer.parseInt(partes[2]);
        List<String> prereqs = new ArrayList<>();
        for (int i = 3; i < partes.length; i++) {
            prereqs.add(partes[i]);
        }
        return new Disciplina(nome, codigo, cargaHoraria, prereqs);
    }
}
