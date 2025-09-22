package TestesdeUnidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Classes.ContaPoupanca;

class ContaPoupancaTeste {

	@Test
	@DisplayName("Deve aplicar rendimento mensal corretamente")
	void deveAplicarRendimentoCorretamente() {
		// define as taxas de 12% ao ano (1% ao mês para simplificar o teste)
		ContaPoupanca conta = new ContaPoupanca("Minha Poupança", 2000.0, 12.0);
        double rendimentoEsperado = (0.12 / 12.0) * 2000.0; // 20.0
        double saldoFinalEsperado = 2000.0 + rendimentoEsperado;

        // aplica o rendimento mensal na conta
        conta.aplicarRendimentoMensal();

        // verifica se o saldo aumentou, se a transação foi registrada, e se a descrição dessa transação está correta
        assertEquals(saldoFinalEsperado, conta.getSaldo(), 0.001); // 0.001 é a margem de erro para doubles
        assertEquals(1, conta.getTransacoes().size());
        assertEquals("Rendimento mensal poupança", conta.getTransacoes().get(0).getDescricao());
	}
	
	@Test
    @DisplayName("Não deve aplicar rendimento se saldo for zero ou negativo")
    void naoDeveAplicarRendimentoComSaldoZero() {
        // cria uma conta com saldo zerado
        ContaPoupanca conta = new ContaPoupanca("Poupança Zerada", 0.0, 12.0);

        // aplica o rendimento mensal (nao vai ser aplicado ja que o saldo é 0)
        conta.aplicarRendimentoMensal();

        // verifica que o saldo permanece zero e que nenhuma transação foi criada
        assertEquals(0.0, conta.getSaldo());
        assertTrue(conta.getTransacoes().isEmpty());
    }

}
