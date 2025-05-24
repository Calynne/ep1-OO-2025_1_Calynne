import java.util.ArrayList;
import java.util.List;

public class Disciplina {
  private String nome;
  private int vagas;
 private List<String> preReq;
  private List<Aluno> alunMatri;

  public Disciplina(String nome, int vagas, List<String> preReq) {
    this.nome = nome;
     this.vagas = vagas;
     this.preReq = preReq;
     this.alunMatri = new ArrayList<>();
  }

 public boolean temVaga() {
    return alunMatri.size() < vagas;
  }

  public boolean matricular(Aluno a) {
    if (temVaga()) {
      alunMatri.add(a);
      return true;
    }
    return false;
  }

  List<String> getPreReq() {
    return preReq;
  }

  @Override
  public String toString() {
    return nome + " (Vagas disponíveis nesta discplina: " + (vagas - alunMatri.size()) + ")";
  }
     public String getNome() {
        return nome;
    }
}