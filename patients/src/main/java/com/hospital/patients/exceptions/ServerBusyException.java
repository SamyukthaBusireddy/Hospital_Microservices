package com.hospital.patients.exceptions;

public class ServerBusyException extends RuntimeException {
    public ServerBusyException(String message) { super( message ); }
}
