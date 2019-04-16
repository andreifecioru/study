#!/usr/bin/env python
from collections import namedtuple
from random import choice

Card = namedtuple('Card', ['rank', 'suit'])

class CardDeck:
  # these are class fields
  ranks = [str(n) for n in range(2, 11)] + list('JQAK')
  suits = 'spades diamonds clubs hearts'.split()

  def __init__(self):
    self._cards = [Card(rank, suit) for rank in self.ranks
                                    for suit in self.suits]

  def __len__(self):
    return len(self._cards)

  def __getitem__(self, pos):
    return self._cards[pos]


suit_values = dict(spades=3, diamonds=2, clubs=1, hearts=1)

def card_value(card):
  rank_value = CardDeck.ranks.index(card.rank)
  return rank_value * len(suit_values) + suit_values[card.suit]


if __name__ == '__main__':
  deck = CardDeck()

  # Deck responds to len()
  print('Deck has {} cards'.format(len(deck)))

  # Deck can be indexed
  print(deck[22])

  # Can pick a random card from deck
  for _ in range(1, 10):
    print("Here's a random card: ", choice(deck))

  # A deck can now be sliced
  print('The first 5 cards in the deck', deck[:5])

  # A deck can be reversed
  print('The first 5 cards of the reversed deck', list(reversed(deck))[:5])

  # A deck iterated over and can be sorted
  for card in sorted(deck, key=card_value):
    print(card)