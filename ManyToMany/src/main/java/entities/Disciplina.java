package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Disciplina
 *
 */
@Entity
@Table(name="disciplina")
public class Disciplina implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private String codigo;
	
	@Column
	private String nome;
	
	@OneToMany(mappedBy = "disciplina")
	private List<AlunoDisciplina> alunos;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<AlunoDisciplina> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoDisciplina> alunos) {
		this.alunos = alunos;
	}
   
}
