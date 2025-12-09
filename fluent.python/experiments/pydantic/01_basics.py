from pydantic import validate_call

@validate_call
def create_user(name: str, age: int | None = None) -> dict[str, str | int | None]:
  return {
    'name': name,
    'age': age,
  }


def main():
  # user = create_user('John', "thirty-two") # this fails with validation error
  # user = create_user('John', "32") # this works because it was able to cast it (however the type checking still fails)
  user = create_user('John', 32) # this works because it was able to cast it
  print(user)

if __name__ == '__main__':
  main()