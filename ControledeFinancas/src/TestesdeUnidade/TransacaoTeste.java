package TestesdeUnidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Classes.Categoria;
import Classes.TipoTransacao;
import Classes.Transacao;
import java.time.LocalDate;

class TransacaoTeste {
	private Categoria categoriaPadrao;
	
	@BeforeEach // metodo que roda ANTES de cada teste para evitar repeticao de codigo nos testes
    void setUp() {
		//cria uma categoria padrao para usar nas transacoes de teste
        categoriaPadrao = new Categoria("Padrão");
    }
	
	@Test
	 @DisplayName("Deve criar uma transação de receita com sucesso")
	void CriarTransacaoReceitaComSucesso() {
		String descricao = "Salário do Mês";
        double valor = 5000.0;
        LocalDate data = LocalDate.now();
        TipoTransacao tipo = TipoTransacao.RECEITA;
        
        Transacao transacao = new Transacao(descricao, valor, data, categoriaPadrao, tipo);
        
        // verifica se os dados foram atribuídos corretamente e se o método de pagamento é nulo
        assertEquals(descricao, transacao.getDescricao());
        assertEquals(valor, transacao.getValor());
        assertEquals(data, transacao.getData());
        assertEquals(categoriaPadrao, transacao.getCategoria());
        assertEquals(tipo, transacao.getTipo());
        assertNull(transacao.getMetodoPagamento()); // para receita, deve ser nulo
	}
	
	@Test
    @DisplayName("Deve criar uma transação de despesa com sucesso")
    void CriarTransacaoDespesaComSucesso() {
        // Arrange
        String descricao = "Aluguel";
        double valor = 1200.0;
        LocalDate data = LocalDate.of(2025, 6, 5);
        TipoTransacao tipo = TipoTransacao.DESPESA;
        String metodoPgto = "Débito Automático";

        Transacao transacao = new Transacao(descricao, valor, data, categoriaPadrao, tipo, metodoPgto);

        // verifica se todos os dados foram atribuídos corretamente 
        assertEquals(descricao, transacao.getDescricao());
        assertEquals(valor, transacao.getValor());
        assertEquals(tipo, transacao.getTipo());
        assertEquals(metodoPgto, transacao.getMetodoPagamento());
    }
	
	@Test
    @DisplayName("Deve lançar exceção para valor de transação negativo ou zero")
    void LancarExcecaoParaValorInvalido() {
        
		// testa se o sistema impede a criação de transações com valores inválidos
		// primeiro tenta com 0.0 e depois com valores negativos
        assertThrows(IllegalArgumentException.class, () -> {
            new Transacao("Invalida", 0.0, LocalDate.now(), categoriaPadrao, TipoTransacao.RECEITA);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Transacao("Invalida", -100.0, LocalDate.now(), categoriaPadrao, TipoTransacao.DESPESA);
        });
    }

}
