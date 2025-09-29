from sqlalchemy import Column, Integer, String, Boolean
from db import Db


class Todo(Db.base):
    __tablename__ = 'todos'

    id = Column(Integer, primary_key=True)
    task = Column(String(512), nullable=False)
    is_completed = Column(Boolean, default=False)

    def __repr__(self):
        return f"Todo(id={self.id}, task={self.task}, is_completed={self.is_completed})"


class TodoRepository:
    def __init__(self, db: Db):
        self.db = db

    def __enter__(self):
        """Enter the context manager - return self to be used in 'with' block"""
        self.session = self.db.get_session()
        return self

    def __exit__(self, exc_type, exc_value, traceback):
        """Exit the context manager - handle cleanup"""
        if exc_type is not None:
            # An exception occurred, rollback the transaction
            print(f"Exception occurred: {exc_type.__name__}: {exc_value}")
            self.session.rollback()
        else:
            # No exception, commit the transaction
            self.session.commit()

        # Close the session
        self.session.close()
        self.session = None

        # Return False to propagate any exception that occurred
        return False

    def create(self, task: str) -> Todo:
        todo = Todo(task=task)
        self.session.add(todo)
        print(f"Created todo: {todo}")
        return todo

    def get_all(self) -> list[Todo]:
        return self.session.query(Todo).all()

    def get_by_id(self, todo_id: int) -> Todo:
        return self.session.query(Todo).filter_by(id=todo_id).first()

    def get_completed(self) -> list[Todo]:
        return self.session.query(Todo).filter_by(is_completed=True).all()

    def get_incomplete(self) -> list[Todo]:
        return self.session.query(Todo).filter_by(is_completed=False).all()

    def delete_by_id(self, todo_id: int) -> bool:
        todo = self.get_by_id(todo_id)
        if todo:
            self.session.delete(todo)
            return True
        return False

    def mark_completed(self, todo_id: int) -> bool:
        todo = self.get_by_id(todo_id)
        if todo:
            todo.is_completed = True
            return True
        return False

    def mark_incomplete(self, todo_id: int) -> bool:
        todo = self.get_by_id(todo_id)
        if todo:
            todo.is_completed = False
            return True
        return False

    def delete_all(self) -> bool:
        self.session.query(Todo).delete()
        return True
