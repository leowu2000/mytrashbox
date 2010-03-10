/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtool;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

/**
 *
 * @author User
 */
public class SplitPageResultSetExtractor implements ResultSetExtractor {

    private final int start;// ��ʼ�к�
    private final int len;// ������ϵĳ���
    private final RowMapper rowMapper;// �а�װ��
    public SplitPageResultSetExtractor(RowMapper rowMapper, int start, int len) {
        Assert.notNull(rowMapper, "RowMapper is required");
        this.rowMapper = rowMapper;
        this.start = start;
        this.len = len;
    }

    /**
     * ����������,���ӿ��Զ����ã�������߲�Ӧ�õ���
     */
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        List result = new ArrayList();
        ResultSetMetaData meta = rs.getMetaData();
        final int cols = meta.getColumnCount();
        String[] code = new String[cols];
        for(int i=0;i<cols;i++){
            code[i]=meta.getColumnName(i+1);
        }
        result.add(code);
        int rowNum = 0;
        int end = start + len;
        point:
        while (rs.next()) {
            ++rowNum;
            if (rowNum < start) {
                continue point;
            } else if (rowNum >= end) {
                break point;
            } else {
                String value[]=new String[cols];
                for(int i=0;i<cols;i++){
                    value[i]=rs.getString(code[i]);
                }
                result.add(value);
            }
        }
        return result;
    }
}


