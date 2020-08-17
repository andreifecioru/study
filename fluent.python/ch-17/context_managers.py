from contextlib import contextmanager


class Person:
    def __init__(self, first_name, last_name):
        self.__first_name = first_name
        self.__last_name = last_name


class PatchPerson:
    @staticmethod
    def __full_name(person):
        return f"{person._Person__first_name} {person._Person__last_name}"

    def __enter__(self):
        Person.full_name = PatchPerson.__full_name

    def __exit__(self, exc_type, exc_value, traceback):
        del Person.full_name


@contextmanager
def patch_person():
    def __full_name(person):
        return f"{person._Person__first_name} {person._Person__last_name}"

    # this part corresponds to the '__enter__' special method
    Person.full_name = __full_name
    try:
        yield
    except Exception as e:
        # allow exception to bubble up (see [1])
        print("Something bad happened")
        raise(e)
    finally:
        # this part corresponds to the '__exit__' special method
        del Person.full_name


if __name__ == "__main__":
    andrei = Person("Andrei", "Fecioru")

    with PatchPerson():
        print(f"[IN] Andrei's full name is: {andrei.full_name()}")

    with patch_person():
        print(f"[IN] Andrei's full name is: {andrei.full_name()}")

    print(f"[OUT] Andrei's full name is: {andrei.full_name()}")
