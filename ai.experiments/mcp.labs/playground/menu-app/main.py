import questionary as qs


if __name__ == '__main__':
    choice = qs.select(
        "What do you want to do?",
        [
            "Hello World",
            "Goodbye World"
        ]).ask()
    print(choice)