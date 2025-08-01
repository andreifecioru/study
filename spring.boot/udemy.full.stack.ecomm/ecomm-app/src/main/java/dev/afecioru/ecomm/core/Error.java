package dev.afecioru.ecomm.core;

public interface Error {
    class GenericError extends RuntimeException {
        public GenericError(String message) { super(message); }
    }

    class NotFoundError extends GenericError {
        public NotFoundError(String message) { super(message); }
    }

    class InvalidStateError extends GenericError {
        public InvalidStateError(String message) { super(message); }
    }

    class InvalidOperationError extends GenericError {
        public InvalidOperationError(String message) { super(message); }
    }
}
