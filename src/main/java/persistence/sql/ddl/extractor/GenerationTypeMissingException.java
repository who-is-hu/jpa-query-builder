package persistence.sql.ddl.extractor;

public class GenerationTypeMissingException extends RuntimeException {
    public GenerationTypeMissingException() {
        super("GenerationType 이 정의되지 않았습니다.");
    }
}
