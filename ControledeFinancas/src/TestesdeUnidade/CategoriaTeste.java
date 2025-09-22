package TestesdeUnidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Classes.Categoria;

class CategoriaTeste {

	@Test
	@DisplayName("Deve criar uma categoria com sucesso com nome válido")
	void CriarCategoriaComNomeValido() {
		String nome = "Alimentação"; //define um nome valido
		
		//utiliza o nome anterior para criar uma categoria
		Categoria categoria = new Categoria(nome);
		
		// verificacao da criacao do objeto e se o nome ta correto
		assertNotNull(categoria);
        assertEquals(nome, categoria.getnome());
	}
	
	@Test
    @DisplayName("Deve lançar exceção ao criar categoria com nome nulo")
    void LancarExcecaoParaNomeNulo() {
       
        String nomeNulo = null; //define um nome nulo

        // verificacao se ao criar nome nulo a exceção é lancada 
        assertThrows(IllegalArgumentException.class, () -> {
            new Categoria(nomeNulo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com nome vazio")
    void LancarExcecaoParaNomeVazio() {
        // Arrange
        String nomeVazio = "   "; //cria um nome vazio ou com espacos

        // verifica se a excecao é lancada ao tentar criar nome vazio
        assertThrows(IllegalArgumentException.class, () -> {
            new Categoria(nomeVazio);
        });
    }

    @Test
    @DisplayName("Deve considerar duas categorias com o mesmo nome como iguais")
    void TestarEqualsHashCodeParaCategoriasIguais() {
        
    	//cria duas categorias iguais
        Categoria cat1 = new Categoria("Lazer");
        Categoria cat2 = new Categoria("Lazer");

        // testa se sao mesmo iguais e tem o mesmo hashcode
        assertTrue(cat1.equals(cat2));
        assertEquals(cat1.hashCode(), cat2.hashCode());
    }

    @Test
    @DisplayName("Deve considerar duas categorias com nomes diferentes como diferentes")
    void TestarEqualsParaCategoriasDiferentes() {
        // define duas categorias diferentes
        Categoria cat1 = new Categoria("Lazer");
        Categoria cat2 = new Categoria("Trabalho");

        // considera elas diferentes
        assertFalse(cat1.equals(cat2));
    }

    @Test
    @DisplayName("Deve retornar o nome da categoria no método toString")
    void TestarToString() {
        // cria uma categoria nova
        String nome = "Moradia";
        Categoria categoria = new Categoria(nome);

        // retorna a nova categoria
        assertEquals(nome, categoria.toString());
    }

}
