package org.acme;

import java.util.List;

public class Pedido {

    private List<ItemPedido> itens;
    private DescontoService descontoService;
    private List<DescontoService> descontoService2;
    private int quant;

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public Pedido(List<ItemPedido> itens, DescontoService descontoService) {
        this.itens = itens;
        this.descontoService = descontoService;
        this.quant = 0;
    }

    public Pedido(List<ItemPedido> itens, List<DescontoService> descontoService2) {
        this.itens = itens;
        this.descontoService2 = descontoService2;
        this.quant = 0;
    }

    public double calcularDescontoPorItem() {
        if(itens.isEmpty()) {
            return 0.0;
        }

        double valorTotal = 0.0;
        double valorItem;

        for(int i = 0; i < itens.size(); i++) {
            valorItem = itens.get(i).getSubtotal();
            double desconto = descontoService2.get(i).calcularDesconto(valorItem);
            valorTotal += desconto;
        }

        return valorTotal;
    }

    public double calcularValorTotal() {
        if(itens.isEmpty()) {
            return 0.0;
        }

        double valorTotal = 0.0;
        for (ItemPedido item : itens) {
            valorTotal += item.getSubtotal();
        }

        double desconto = descontoService.calcularDesconto(valorTotal);
        this.setQuant(this.getQuant() + 1);
        
        if(desconto > valorTotal) {
            throw new IllegalArgumentException();
        }

        return valorTotal - desconto;
    }
}