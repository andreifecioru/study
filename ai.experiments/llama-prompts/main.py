from agents import *


if __name__ == '__main__':
    chat_bot = ChatBot(name='Chatty', description='A very simple chat bot.')
    print(chat_bot.system_prompt)
    question = input('Ask a question: ')
    print(f'Asking {chat_bot.name} a question...')
    answer = chat_bot.ask(question)
    print(f'{chat_bot.name} says: ')
    print(answer)
    print('--------------------')
