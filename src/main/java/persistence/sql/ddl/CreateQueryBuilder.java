package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.mapping.ColumnData;
import persistence.sql.mapping.Columns;
import persistence.sql.mapping.TableData;

import java.util.ArrayList;

public class CreateQueryBuilder {
    private final Dialect dialect;
    private final TableData tableData;
    private final Columns columns;


    public CreateQueryBuilder(Dialect dialect, Class<?> clazz) {
        this.dialect = dialect;
        this.tableData = TableData.from(clazz);
        this.columns = Columns.createColumns(clazz);
    }

    public String toQuery() {
        final String tableNameClause = tableData.getName();
        final String columnClause = getColumnClause();
        final String keyClause = getKeyClause();

        return String.format("CREATE TABLE %s (%s, %s)", tableNameClause, columnClause, keyClause);
    }

    private String getColumnClause() {
        ArrayList<String> columnStrings = new ArrayList<>();
        for(ColumnData columnData : columns) {
            columnStrings.add(getColumnString(columnData));
        }
        return String.join(", ", columnStrings);
    }

    private String getColumnString(ColumnData columnData) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(columnData.getName());
        stringBuilder.append(" ");
        stringBuilder.append(dialect.mapDataType(columnData.getType()));

        if(columnData.isNotNullable()) {
            stringBuilder.append(" NOT NULL");
        }
        if (columnData.hasGenerationType()) {
            stringBuilder.append(" ");
            stringBuilder.append(dialect.mapGenerationType(columnData.getGenerationType()));
        }

        return stringBuilder.toString();
    }

    private String getKeyClause() {
        ColumnData keyColumn = columns.getKeyColumn();
        return String.format("%s KEY (%s)", dialect.mapKeyType(keyColumn.getKeyType()), keyColumn.getName());
    }
}
