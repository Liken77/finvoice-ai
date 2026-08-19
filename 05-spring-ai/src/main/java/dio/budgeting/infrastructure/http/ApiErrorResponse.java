package dio.budgeting.infrastructure.http;

public record ApiErrorResponse(int status, String message) {
}
