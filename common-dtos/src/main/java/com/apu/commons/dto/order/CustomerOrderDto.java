package com.apu.commons.dto.order;

import com.apu.commons.dto.CommonDto;
import com.apu.commons.dto.product.ProductSummaryDto;
import com.apu.commons.event.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDto extends CommonDto {
    private Long id;

    @NotNull(message = "Customer Id must not be null!")
    private Long customerId;

    private LocalDate orderPlacingDate;

    @NotNull(message = "Order details can not be null!")
    @NotEmpty(message = "Order details can not be empty!")
    private String orderDetails;

    private LocalDate expectedDeliveryDate;

    @NotNull(message = "Product list must not be null!")
    @Size(min = 1, message = "Product list minimum size must be 1!")
    private List<ProductSummaryDto> productList = new ArrayList<>();

    private Double totalAmount;
    private Double paymentAmount;

    private OrderStatus orderStatus;
}
