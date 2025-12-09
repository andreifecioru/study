from collections.abc import Iterator
from random import choice
from typing import NamedTuple, overload


class Card(NamedTuple):
  rank: str
  suit: str


class CardDeck:
  # these are class fields (best practice: make them immutable)
  ranks = tuple([str(n) for n in range(2, 11)] + list("JQAK"))
  suits = ("spades", "diamonds", "clubs", "hearts")

  def __init__(self) -> None:
    self._cards = [Card(rank, suit) for rank in self.ranks for suit in self.suits]

  def __len__(self) -> int:
    return len(self._cards)

  def __iter__(self) -> Iterator[Card]:
    return iter(self._cards)

  # use overloads so static checkers know an int returns a single Card and a slice returns a list[Card]
  @overload
  def __getitem__(self, pos: int) -> Card: ...
  @overload
  def __getitem__(self, pos: slice) -> list[Card]: ...

  def __getitem__(self, pos: int | slice) -> Card | list[Card]:
    return self._cards[pos]


suit_values = {"spades": 3, "diamonds": 2, "clubs": 1, "hearts": 1}


def card_value(card: Card) -> int:
  rank_value = CardDeck.ranks.index(card.rank)
  return rank_value * len(suit_values) + suit_values[card.suit]


if __name__ == "__main__":
  deck = CardDeck()

  # Deck responds to len()
  print(f"Deck has {len(deck)} cards")

  # Deck can be indexed
  print(deck[22])

  # Can pick a random card from deck
  for _ in range(1, 10):
    print("Here's a random card: ", choice(deck))

  # A deck can now be sliced
  print("The first 5 cards in the deck", deck[:5])

  # A deck can be reversed
  print("The first 5 cards of the reversed deck", list(reversed(deck))[:5])

  # A deck iterated over and can be sorted
  for card in sorted(deck, key=card_value):
    print(card)
