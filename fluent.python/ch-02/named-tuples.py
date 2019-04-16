#!/usr/bin/env python

from collections import namedtuple

City = namedtuple('City', ['name', 'location'])

if __name__ == '__main__':
  ny = City('NewYork', (20.3, 45.6))
  print(ny.name)
  print(ny.location)

  # Attribute names are class fields
  print(City._fields)

  # Create a namedtuple instance out of an iterable
  barcelona_data = ('Barcelona', (11.2, 34.8))
  barcelona = City._make(barcelona_data)
  print(barcelona)

  # ... which is the same as
  barcelona = City(*barcelona_data)
  print(barcelona)

  # Expose a namedtuple instace as a dictionary (OrderedDict)
  print(barcelona._asdict())