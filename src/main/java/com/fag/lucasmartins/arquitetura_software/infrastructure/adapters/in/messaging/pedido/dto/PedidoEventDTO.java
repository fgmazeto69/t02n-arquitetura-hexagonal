package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto;

import java.util.List;

public class PedidoEventDTO {

    private String zipCode;

    private Long customerId;

    private List<PedidoItemEventDTO> orderItems;

    private String origin;

    private String occurredAt;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<PedidoItemEventDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<PedidoItemEventDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(String occurredAt) {
        this.occurredAt = occurredAt;
    }

    public static class PedidoItemEventDTO {

        private Integer sku;

        private int amount;

        public Integer getSku() {
            return sku;
        }

        public void setSku(Integer sku) {
            this.sku = sku;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
