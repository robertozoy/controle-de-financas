package TestesdeUnidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Classes.Usuario;
import Classes.ContaCorrente;
import Classes.ContaPoupanca;
import Classes.Conta;

import java.util.Optional;

class UsuarioTeste {
	
	private Usuario usuario;

	//prepara um Usuario novo antes de cada teste
    @BeforeEach
    void setUp() {
        usuario = new Usuario("Maria Oliveira"); 
    }

	@Test
	@DisplayName("Deve adicionar uma conta com sucesso")
	void AdicionarContaComSucesso() {
		
		Conta conta = new ContaCorrente("Conta Salário", 500.0, 100.0);

        // adiciona a conta ao usuario
        usuario.adicionarConta(conta);

        // verifica se a conta foi adicionada e se o tamanho da lista de contas é igual a 1
        assertEquals(1, usuario.getContas().size());
        assertTrue(usuario.getContas().contains(conta));
	}
	
	@Test
    @DisplayName("Deve lançar exceção ao adicionar conta com nome duplicado")
    void LancarExcecaoAoAdicionarContaDuplicada() {
        // cria duas contas com mesmo nome e adiciona a primeira
        Conta conta1 = new ContaCorrente("Conta Principal", 100.0, 50.0);
        Conta conta2 = new ContaPoupanca("Conta Principal", 200.0, 1.0); // Mesmo nome
        usuario.adicionarConta(conta1);

        // lanca excecao quando tenta adicionar a segunda
        assertThrows(IllegalArgumentException.class, () -> {
            usuario.adicionarConta(conta2);
        });
    }
	
	@Test
    @DisplayName("Deve encontrar conta por nome")
    void EncontrarContaPorNome() {
        // cria e adiciona uma conta com um nome definido
        String nomeConta = "Minha Carteira";
        Conta carteira = new ContaCorrente(nomeConta, 300.0, 0);
        usuario.adicionarConta(carteira);

        // busco por nome, encontra e retora
        Optional<Conta> contaEncontrada = usuario.getContaPorNome(nomeConta);
        Optional<Conta> contaNaoEncontrada = usuario.getContaPorNome("Inexistente");

        // busca por um nome inexistente
        assertTrue(contaEncontrada.isPresent());
        assertEquals(carteira, contaEncontrada.get());
        assertFalse(contaNaoEncontrada.isPresent());
    }

}
