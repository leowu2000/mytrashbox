/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtool;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author User
 */
public class PageTemplate extends JdbcTemplate {

    private DataSource dataSource;

    /**
     * 默认构造器，调用此方法初始化，需要调用setDataSource设置数据源
     */
    public PageTemplate() {
    }

    /**
     * 初始构造器
     *
     * @param dataSource
     * 数据源
     */
    public PageTemplate(SingleConnectionDataSource dataSource) {
        this.dataSource = dataSource;
        super.setDataSource(dataSource);
    }

    /**
     * 普通分页查询<br>
     * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢出</b>
     * @see #setFetchSize(int)
     * @see #setMaxRows(int)
     * @param sql
     * 查询的sql语句
     * @param startRow
     * 起始行
     * @param rowsCount
     * 获取的行数
     * @return
     * @throws DataAccessException
     */
    @SuppressWarnings("unchecked")
    public List<Map> querySP(String sql, int startRow, int rowsCount)
            throws DataAccessException {
        return querySP(sql, startRow, rowsCount, getColumnMapRowMapper());
    }

    /**
     * 自定义行包装器查询<br>
     * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢出</b>
     * @see #setFetchSize(int)
     * @see #setMaxRows(int)
     * @param sql
     * 查询的sql语句
     * @param startRow
     * 起始行
     * @param rowsCount
     * 获取的行数
     * @param rowMapper
     * 行包装器
     * @return
     * @throws DataAccessException
     */
    @SuppressWarnings("unchecked")
    public List<Map> querySP(String sql, int startRow, int rowsCount, RowMapper rowMapper)
            throws DataAccessException {
        return (List) query(sql, new SplitPageResultSetExtractor(rowMapper, startRow,
                rowsCount));
    }
//    public List<Map> querySP(String sql, int startRow, int rowsCount, RowMapper rowMapper)
//            throws DataAccessException {
//        return (List) query(sql, new SplitPageResultSetExtractor(rowMapper, startRow,
//                rowsCount));
//    }
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        super.setDataSource(dataSource);
    }
}

