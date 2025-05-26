import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String professor;
    private String semestre;
    private String avaliacao;
    private boolean presencial;
    private String sala;
    private String horario;
    private int capacidadeMaxima;
    private List<Aluno> alunosMatriculados;

    public Turma(String professor, String semestre, String avaliacao, boolean presencial, String sala, String horario, int capacidadeMaxima) {
        this.professor = professor;
        this.semestre = semestre;
        this.avaliacao = avaliacao;
        this.presencial = presencial;
        this.sala = presencial ? sala : "Remota";
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = new ArrayList<>();
    }

    public boolean temVaga() {
        return alunosMatriculados.size() < capacidadeMaxima;
    }

    public void matricular(Aluno aluno) {
        if (!alunosMatriculados.contains(aluno) && temVaga()) {
            alunosMatriculados.add(aluno);
        }
    }

    public void removerAluno(Aluno aluno) {
        alunosMatriculados.remove(aluno);
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public String getHorario() {
        return horario;
    }

    public String getProfessor() {
        return professor;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public String getSala() {
        return sala;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    
    @Override
    public String toString() {
        return "Professor: " + professor +
                ", Semestre: " + semestre +
                ", Avaliação: " + avaliacao +
                ", Modalidade: " + (presencial ? "Presencial" : "Remota") +
                (presencial ? ", Sala: " + sala : "") +
                ", Horário: " + horario +
                ", Capacidade: " + capacidadeMaxima +
                ", Matriculados: " + alunosMatriculados.size();
    }

    // ✅ Converte turma para formato de texto para salvar
    public String toCSV() {
        return professor + ";" +
               semestre + ";" +
               avaliacao + ";" +
               (presencial ? "1" : "0") + ";" +
               sala + ";" +
               horario + ";" +
               capacidadeMaxima;
    }

    // ✅ Cria turma a partir de uma linha do .txt
    public static Turma fromCSV(String linha) {
        String[] partes = linha.split(";");
        if (partes.length != 7) return null;

        String professor = partes[0];
        String semestre = partes[1];
        String avaliacao = partes[2];
        boolean presencial = partes[3].equals("1");
        String sala = partes[4];
        String horario = partes[5];
        int capacidade = Integer.parseInt(partes[6]);

        return new Turma(professor, semestre, avaliacao, presencial, sala, horario, capacidade);
    }
}
