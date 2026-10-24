package com.hospital.appointments.exceptions;

public class ServerBusyException extends RuntimeException {
    public ServerBusyException(String message) { super( message ); }
}
