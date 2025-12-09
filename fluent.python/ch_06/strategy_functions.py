from collections.abc import Callable

type Promotion = Callable[[Order], float]


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
      return self.total() - self.promotion(self)

    return self.total()


def new_client_promotion(order: Order) -> float:
  return order.total() * 0.1


def bulk_promotion(order: Order) -> float:
  return order.total() * 0.3


def best_promo(order: Order) -> float:
  promos = [globals()[name] for name in globals() if name.endswith("_promotion")]
  return max(promo(order) for promo in promos)


if __name__ == "__main__":
  cart = [LineItem("apples", 10), LineItem("bananas", 20), LineItem("carrots", 70)]

  order = Order(cart)
  print(f"Order due (no promotion): {order.due():4.2f}$")

  order = Order(cart, promotion=new_client_promotion)
  print(f"Order total (new client promotion): {order.due():4.2f}$")

  order = Order(cart, promotion=bulk_promotion)
  print(f"Order total (bulk promotion): {order.due():4.2f}$")

  order = Order(cart, promotion=best_promo)
  print(f"Order total (bestpromotion): {order.due():4.2f}$")
