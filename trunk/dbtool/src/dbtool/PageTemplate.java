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
     * Ĭ�Ϲ����������ô˷�����ʼ������Ҫ����setDataSource��������Դ
     */
    public PageTemplate() {
    }

    /**
     * ��ʼ������
     *
     * @param dataSource
     * ����Դ
     */
    public PageTemplate(SingleConnectionDataSource dataSource) {
        this.dataSource = dataSource;
        super.setDataSource(dataSource);
    }

    /**
     * ��ͨ��ҳ��ѯ<br>
     * <b>��������ϱȽϴ�Ӧ�õ���setFetchsize() ��setMaxRow��������������һ�£�������ڴ����</b>
     * @see #setFetchSize(int)
     * @see #setMaxRows(int)
     * @param sql
     * ��ѯ��sql���
     * @param startRow
     * ��ʼ��
     * @param rowsCount
     * ��ȡ������
     * @return
     * @throws DataAccessException
     */
    @SuppressWarnings("unchecked")
    public List<Map> querySP(String sql, int startRow, int rowsCount)
            throws DataAccessException {
        return querySP(sql, startRow, rowsCount, getColumnMapRowMapper());
    }

    /**
     * �Զ����а�װ����ѯ<br>
     * <b>��������ϱȽϴ�Ӧ�õ���setFetchsize() ��setMaxRow��������������һ�£�������ڴ����</b>
     * @see #setFetchSize(int)
     * @see #setMaxRows(int)
     * @param sql
     * ��ѯ��sql���
     * @param startRow
     * ��ʼ��
     * @param rowsCount
     * ��ȡ������
     * @param rowMapper
     * �а�װ��
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

