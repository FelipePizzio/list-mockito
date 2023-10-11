package org.acme;

public class ItemPedido {

    private String name;
    private double valor;
    private int quantidade;

    public ItemPedido(String name, double valor, int quantidade) {
        this.name = name;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return valor * quantidade;
    }
    
}
