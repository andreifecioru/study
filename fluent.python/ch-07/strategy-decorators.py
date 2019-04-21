#!/usr/bin/env python

# global promo registry
promos = []
def promotion(promo):
  promos.append(promo)
  return promo


class LineItem:
  def __init__(self, name, price):
    self.name = name
    self.price = price


class Order:
  def __init__(self, cart, promotion=None):
    self.cart = cart
    self.promotion = promotion

  def total(self):
    return sum(item.price for item in cart)

  def due(self):
    if self.promotion:
      return self.total() - self.promotion(self)
    
    return self.total()
    

@promotion
def new_client_promotion(order):
  return order.total() * .1

@promotion
def bulk_promotion(order):
  return order.total() * .3


def best_promo(order):
  return max(promo(order) for promo in promos)


if __name__ == '__main__':
  cart = [
    LineItem('apples', 10),
    LineItem('bananas', 20),
    LineItem('carrots', 70)
  ]

  order = Order(cart)
  print("Order due (no promotion): {:4.2f}$".format(order.due()))

  order = Order(cart, promotion=new_client_promotion)
  print("Order total (new client promotion): {:4.2f}$".format(order.due()))

  order = Order(cart, promotion=bulk_promotion)
  print("Order total (bulk promotion): {:4.2f}$".format(order.due()))

  order = Order(cart, promotion=best_promo)
  print("Order total (bestpromotion): {:4.2f}$".format(order.due()))
