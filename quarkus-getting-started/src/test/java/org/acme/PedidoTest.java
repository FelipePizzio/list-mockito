package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PedidoTest {

    private Pedido pedido;
    private DescontoService descontoService;    
    private DescontoService descontoService2;
    private List<DescontoService> descontos;
    private List<ItemPedido> itens;


    @Test
    void testCalcularValorTotalComDesconto() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(35.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));

        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        assertEquals(315.0, valorTotalComDesconto);
    }

    @Test
    void testCalcularValorTotalSemDesconto() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(0.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));

        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        assertEquals(350.0, valorTotalComDesconto);
    }

    @Test
    void testCalcularValorTotalComDescontoMaiorQueValorTotal() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(400.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));

        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        assertEquals(0.0, valorTotalComDesconto);
    }

    @Test
    void testCalcularValorTotalComDescontoMaiorQueValorTotalRetornaException() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            descontoService.calcularDesconto(anyDouble());
        });
    }

    @Test
    void testCalcularValorTotalComListaVazia() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(0.0);

        itens = new ArrayList<>();

        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        assertEquals(0.0, valorTotalComDesconto);
    }

    @Test
    void testDescontoPorItem() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(90.0); //10% desconto

        descontoService2 = mock(DescontoService.class);
        when(descontoService2.calcularDesconto(anyDouble())).thenReturn(40.0); //20% desconto

        descontos = new ArrayList<>();
        descontos.add(descontoService);
        descontos.add(descontoService2);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 1));
        itens.add(new ItemPedido("Item 2", 50.0, 1));

        pedido = new Pedido(itens, descontos);

        double valorTotalComDesconto = pedido.calcularDescontoPorItem();

        assertEquals(130.0, valorTotalComDesconto);
    }

    @Test
    void testMetodoCalcularDescontoChamado1VezListaNaoVazia() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(0.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));

        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        assertEquals(1, pedido.getQuant());
    }

    @Test
    void testMetodoCalcularDescontoChamado0VezesListaVazia() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(0.0);

        itens = new ArrayList<>();

        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        assertEquals(0, pedido.getQuant());
    }
}
