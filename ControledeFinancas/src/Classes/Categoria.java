package Classes;

import java.util.Objects;

public class Categoria {
	private String nome;
	private String descricao;
	
	// construtores + tratamento de erro para caso o nome da categoria for nulo ou vazio.
	public Categoria(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome categoria não pode ser nulo ou vazio.");
		}
		this.nome = nome.trim();
	}
	
	// contrutor secundario
	public Categoria(String nome, String descricao) {
		this(nome);
		this.descricao = descricao;
	}
	
	public String getnome() {
		return nome;
	}
	
	//setnome e tratamento de erro para categoria nula e vazia
	public void setnome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome da categoria não pode ser nulo ou vazio.");
		}
		this.nome = nome.trim();
	}
	
	public String getdescricao() {
		return descricao;
	}
	
	public void setdescricao(String descricao) {
		this.descricao = descricao;
	}
	
	// fornece uma representação em string de um objeto 
	// usamos pra printar um objeto diretamente
	@Override
    public String toString() {
        return nome;
    }
	
	//usamos pra verificar se um objeto esta na mesma posicao de memoria
	@Override
    public boolean equals(Object o) {
        if (this == o) return true; // retorna true se as duas referencias apontam para o msm lugar
        if (o == null || getClass() != o.getClass()) return false; // Verifica se é nulo ou de classe diferente
        Categoria categoria = (Categoria) o;
        return nome.equals(categoria.nome);
    }
	
	//retorna um valor de código hash inteiro para o objeto
	// se dois objetos forem considerados "iguais" pelo equals, eles vao ter o mesmo hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
	
	
}
