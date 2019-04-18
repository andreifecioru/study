#!/usr/bin/env python
from abc import ABC, abstractmethod


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
      return self.total() - self.promotion.discount(self)
    
    return self.total()
    

class Promotion(ABC):
  @abstractmethod
  def discount(self, order):
    '''Apply discount'''


class NewClientPromotion(Promotion):
  def discount(self, order):
    return order.total() * .1


class BulkPromotion(Promotion):
  def discount(self, order):
    return order.total() * .3


if __name__ == '__main__':
  cart = [
    LineItem('apples', 10),
    LineItem('bananas', 20),
    LineItem('carrots', 70)
  ]

  order = Order(cart)
  print("Order due (no promotion): {:4.2f}$".format(order.due()))

  order = Order(cart, promotion=NewClientPromotion())
  print("Order total (new client promotion): {:4.2f}$".format(order.due()))

  order = Order(cart, promotion=BulkPromotion())
  print("Order total (bulk promotion): {:4.2f}$".format(order.due()))

