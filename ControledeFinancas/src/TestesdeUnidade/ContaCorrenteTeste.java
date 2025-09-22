package TestesdeUnidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Classes.Categoria;
import Classes.ContaCorrente;
import Classes.TipoTransacao;
import Classes.Transacao;

import java.time.LocalDate;

class ContaCorrenteTeste {

	private ContaCorrente conta;

	// cria uma nova conta corrente chamada "Banco Principal" antes de cada teste
	// garantindo que cada teste começe do zero
    @BeforeEach
    void setUp() {
        conta = new ContaCorrente("Banco Principal", 1000.0, 500.0);
    }
    
	@Test
	@DisplayName("Deve adicionar uma transação de receita e aumentar o saldo")
	void AdicionarReceitaCorretamente() {
		
		// cria transação do tipo receita e adiciona à conta.
		double valorReceita = 200.0;
        Transacao receita = new Transacao("Depósito", valorReceita, LocalDate.now(), new Categoria("Entradas"), TipoTransacao.RECEITA);
        double saldoEsperado = 1000.0 + valorReceita;
        conta.adicionarTransacao(receita);

        // verifica se o saldo foi atualizado corretamente se a transação foi armazenado e se o número de transações é 1
        assertEquals(saldoEsperado, conta.getSaldo());
        assertTrue(conta.getTransacoes().contains(receita));
        assertEquals(1, conta.getTransacoes().size());
        
        // garante que a conta está somando receitas corretamente.
	}
	
	@Test
    @DisplayName("Deve adicionar uma transação de despesa e diminuir o saldo")
    void AdicionarDespesaCorretamente() {
       
		// cria uma despesa de R$ 150,00 com método de pagamento debito
        double valorDespesa = 150.0;
        Transacao despesa = new Transacao("Supermercado", valorDespesa, LocalDate.now(), new Categoria("Compras"), TipoTransacao.DESPESA, "Débito");
        // subtrai a despesa do valor inicial e adiciona a despesa
        double saldoEsperado = 1000.0 - valorDespesa;
        conta.adicionarTransacao(despesa);

        // Verifica se o saldo caiu para R$ 850,00 e se a transação foi registrada de forma correta
        assertEquals(saldoEsperado, conta.getSaldo());
        assertTrue(conta.getTransacoes().contains(despesa));
    }

	@Test
    @DisplayName("Deve calcular o saldo disponível corretamente (saldo + limite)")
    void CalcularSaldoDisponivel() {
        double saldoDisponivelEsperado = 1000.0 + 500.0;
        double saldoDisponivelAtual = conta.getSaldoDisponivel();

        //testa se o saldo disponível está correto
        assertEquals(saldoDisponivelEsperado, saldoDisponivelAtual);
    }

	@Test
	@DisplayName("Não deve permitir adicionar transação nula")
	void naoDevePermitirTransacaoNula() {
	    // testa se a transacao é nula e lanca exceção
	    assertThrows(IllegalArgumentException.class, () -> {
	        conta.adicionarTransacao(null);
	    });
	}

}
