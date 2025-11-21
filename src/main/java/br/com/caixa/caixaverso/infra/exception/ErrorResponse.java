package br.com.caixa.caixaverso.infra.exception;

public class ErrorResponse {

    private String errorId;
    private int status;
    private String error;
    private String message;
    private String timestamp;

    public ErrorResponse() {}

    public ErrorResponse(String errorId, int status, String error, String message, String timestamp) {
        this.errorId = errorId;
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getErrorId() { return errorId; }
    public void setErrorId(String errorId) { this.errorId = errorId; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
