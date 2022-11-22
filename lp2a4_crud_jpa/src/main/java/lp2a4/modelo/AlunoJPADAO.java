package lp2a4.modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AlunoJPADAO implements AlunoDAO{
	
	private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test");
	private EntityManager emanager = emfactory.createEntityManager();

	@Override
	public boolean create(AlunoPOJO aluno) {
		//É necessário colocar a transaction pois o autocommit é desativado por padrão.
		emanager.getTransaction().begin();
		emanager.persist(aluno);
		emanager.getTransaction().commit();
		return true;
	}

	@Override
	public AlunoPOJO retrieve(String matricula) {
		AlunoPOJO aluno = emanager.find(AlunoPOJO.class, matricula);
		return aluno;
	}

	@Override
	public boolean update(AlunoPOJO aluno) {
		emanager.getTransaction().begin();
		emanager.merge(aluno);
		emanager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean delete(String matricula) {
		//Para remoção é necessário utilizar um merge, para remover o destaque do objeto. Essa função funde o objeto com os dados vindos do banco.
		//Também é possível utilizar um find, com em.find(estudanteDelete.getClass(), estudanteDelete.getMatricula());
		
		AlunoPOJO aluno = new AlunoPOJO();
		aluno.setMatricula(matricula);
		emanager.getTransaction().begin();
		emanager.remove(emanager.merge(aluno));
		emanager.getTransaction().commit();		
		return true;
	}	
}
