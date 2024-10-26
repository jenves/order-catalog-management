package br.com.order.catalog.management.domain;

import br.com.order.catalog.management.domain.order.Order;
import br.com.order.catalog.management.domain.order.OrderItem;
import br.com.order.catalog.management.domain.order.OrderStatus;
import br.com.order.catalog.management.domain.product.Product;
import br.com.order.catalog.management.domain.product.ProductType;
import br.com.order.catalog.management.exceptions.InvalidItemExceptionException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTest {

  @Test
  public void givenAValidParams_whenCallNewOrder_thenInstantiate() {

    var expectedOrderStatus = OrderStatus.OPEN;

    var expectedProduct1 = Product.newProduct("product1", BigDecimal.valueOf(100),
        ProductType.PRODUCT,
        true);

    var expectedProduct2 = Product.newProduct("product2", BigDecimal.valueOf(100),
        ProductType.PRODUCT,
        true);

    var expectedProduct3 = Product.newProduct("product3", BigDecimal.valueOf(50),
        ProductType.SERVICE,
        true);

    var expectedDiscount = 10;

    var expectedItems = Set.of(
        OrderItem.newOrderItem(expectedProduct1, 1),
        OrderItem.newOrderItem(expectedProduct2, 1),
        OrderItem.newOrderItem(expectedProduct3, 1)
    );

    var acturalOrder = Order.newOrder(expectedOrderStatus, expectedItems, expectedDiscount);

    Assertions.assertNotNull(acturalOrder);
    Assertions.assertNotNull(acturalOrder.getId());
    Assertions.assertNotNull(acturalOrder.getStatus());
    Assertions.assertNotNull(acturalOrder.getItems());
    Assertions.assertNotNull(acturalOrder.getTotal());
    Assertions.assertNotNull(acturalOrder.getStatus());
  }

  @Test
  public void givenAInactiveItem_whenCallNewOrder_thenShouldReturnError() {

    var expectedOrderStatus = OrderStatus.OPEN;

    var expectedProduct1 = Product.newProduct("product1", BigDecimal.valueOf(100),
        ProductType.PRODUCT,
        true);

    var expectedProduct2 = Product.newProduct("product2", BigDecimal.valueOf(100),
        ProductType.PRODUCT,
        false);

    var expectedDiscount = 10;

    var expectedItems = Set.of(
        OrderItem.newOrderItem(expectedProduct1, 1),
        OrderItem.newOrderItem(expectedProduct2, 1)
    );

    Assertions.assertThrows(InvalidItemExceptionException.class,
        () -> Order.newOrder(expectedOrderStatus, expectedItems, expectedDiscount));

  }

  @Nested
  class OrderTotalTest {

    private static final Integer MONEY_SCALE = 2;

    private static Stream<Arguments> provideOrderTestCases() {
      return Stream.of(
          Arguments.of(
              List.of(
                  Product.newProduct("product1", new BigDecimal("50.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product2", new BigDecimal("30.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product3", new BigDecimal("10.00"), ProductType.SERVICE, true)
              ),
              10,
              new BigDecimal("82.00").setScale(MONEY_SCALE, RoundingMode.HALF_EVEN),
              OrderStatus.OPEN
          ),
          Arguments.of(
              List.of(
                  Product.newProduct("product1", new BigDecimal("70.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product2", new BigDecimal("40.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product3", new BigDecimal("20.00"), ProductType.SERVICE, true)
              ),
              15,
              new BigDecimal("113.50").setScale(MONEY_SCALE, RoundingMode.HALF_EVEN),
              OrderStatus.OPEN
          ),
          Arguments.of(
              List.of(
                  Product.newProduct("product1", new BigDecimal("60.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product2", new BigDecimal("50.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product3", new BigDecimal("30.00"), ProductType.SERVICE, true)
              ),
              17,
              new BigDecimal("121.30").setScale(MONEY_SCALE, RoundingMode.HALF_EVEN),
              OrderStatus.OPEN
          ),
          Arguments.of(
              List.of(
                  Product.newProduct("product1", new BigDecimal("60.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product2", new BigDecimal("50.00"), ProductType.PRODUCT,
                      true),
                  Product.newProduct("product3", new BigDecimal("30.00"), ProductType.SERVICE, true)
              ),
              17,
              new BigDecimal("140.00").setScale(MONEY_SCALE, RoundingMode.HALF_EVEN),
              OrderStatus.CLOSED
          )
      );
    }

    @ParameterizedTest
    @MethodSource("provideOrderTestCases")
    public void givenAValidParams_whenCallNewOrder_thenCalculateTotal(List<Product> products,
        int discount, BigDecimal expectedTotal, OrderStatus expectedOrderStatus) {

      var expectedItems = products.stream()
          .map(product -> OrderItem.newOrderItem(product, 1))
          .collect(Collectors.toSet());

      var productsTotal = products.stream()
          .filter(product -> product.getType() == ProductType.PRODUCT)
          .map(Product::getPrice)
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      var productsDiscount = Objects.equals(expectedOrderStatus, OrderStatus.OPEN) ? productsTotal
          .multiply(
              BigDecimal.valueOf(discount)
                  .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN)) : BigDecimal.ZERO;

      var calculatedExpectedTotal = products.stream()
          .map(Product::getPrice)
          .reduce(BigDecimal.ZERO, BigDecimal::add)
          .subtract(productsDiscount)
          .setScale(MONEY_SCALE, RoundingMode.HALF_EVEN);

      var actualOrder = Order.newOrder(expectedOrderStatus, expectedItems, discount);

      Assertions.assertEquals(expectedTotal, actualOrder.getTotal());
      Assertions.assertEquals(calculatedExpectedTotal, actualOrder.getTotal());
    }

  }


}
