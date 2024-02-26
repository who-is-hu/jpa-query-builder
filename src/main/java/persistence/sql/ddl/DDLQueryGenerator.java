package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.mapping.ColumnData;
import persistence.sql.mapping.Columns;
import persistence.sql.mapping.TableData;
import persistence.sql.mapping.TableExtractor;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DDLQueryGenerator {
    private final Dialect dialect;
    private final TableData tableData;
    private final List<ColumnData> columns;

    public DDLQueryGenerator(Dialect dialect, Class<?> clazz) {
        this.dialect = dialect;
        this.tableData = new TableExtractor(clazz).createTable();
        this.columns = new ColumnExtractor(clazz).createColumns();
    }

    public String generateDropTableQuery() {
        return String.format("DROP TABLE %s", tableData.getName());
    }

    public String generateCreateQuery() {
        final String tableNameClause = tableData.getName();
        final String columnClause = getColumnClause();
        final String keyClause = getKeyClause();

        return String.format("CREATE TABLE %s (%s, %s)", tableNameClause, columnClause, keyClause);
    }

    private String getColumnClause() {
        return columns
                .stream()
                .map(this::getColumnString)
                .collect(Collectors.joining(", "));
    }

    private String getColumnString(ColumnData columnData) {
        String result = String.format("%s %s", columnData.getName(), dialect.mapDataType(columnData.getType()));
        if(columnData.isNotNullable()) {
            result += " NOT NULL";
        }
        if (columnData.hasGenerationType()) {
            result += String.format(" %s", dialect.mapGenerationType(columnData.getGenerationType()));
        }
        return result;
    }

    private String getKeyClause() {
        return columns.stream()
                .filter(ColumnData::hasKeyType)
                .map(this::getKeyString)
                .collect(Collectors.joining(" ,"));
    }

    private String getKeyString(ColumnData columnData) {
        return String.format("%s KEY (%s)", dialect.mapKeyType(columnData.getKeyType()), columnData.getName());
    }
}
