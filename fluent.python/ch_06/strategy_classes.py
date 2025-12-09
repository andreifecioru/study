from abc import ABC, abstractmethod


class Promotion(ABC):
  @abstractmethod
  def discount(
    self,
    order: "Order",
  ) -> float:  # Order is forward defined (need to use quotes here)
    """Apply discount."""


class LineItem:
  def __init__(self, name: str, price: float) -> None:
    self.name = name
    self.price = price


class Order:
  def __init__(self, cart: list[LineItem], promotion: Promotion | None = None) -> None:
    self.cart = cart
    self.promotion = promotion

  def total(self) -> float:
    return sum(item.price for item in cart)

  def due(self) -> float:
    if self.promotion:
      return self.total() - self.promotion.discount(self)

    return self.total()


class NewClientPromotion(Promotion):
  def discount(self, order: Order) -> float:
    return order.total() * 0.1


class BulkPromotion(Promotion):
  def discount(self, order: Order) -> float:
    return order.total() * 0.3


if __name__ == "__main__":
  cart = [LineItem("apples", 10), LineItem("bananas", 20), LineItem("carrots", 70)]

  order = Order(cart)
  print(f"Order due (no promotion): {order.due():4.2f}$")

  order = Order(cart, promotion=NewClientPromotion())
  print(f"Order total (new client promotion): {order.due():4.2f}$")

  order = Order(cart, promotion=BulkPromotion())
  print(f"Order total (bulk promotion): {order.due():4.2f}$")
