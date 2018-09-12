package com.ha.cjy.common.util.download.db;

import com.ha.cjy.common.util.download.BaseDownloadStateFactory;
import com.ha.cjy.common.util.download.intf.IDownloadState;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;

/**
 * 如果字段是类类型，则这个持久化器会将字段转换成JSON字符串，同时也会将字符串反序列化为JAVA类
 * 
 * @author zhangjinming
 * 
 */
public class JsonPersister extends BaseDataType implements DataPersister {
	private static JsonPersister upgradeInfoPersister;

	public static JsonPersister getSingleton() {
		if (upgradeInfoPersister == null) {
			upgradeInfoPersister = new JsonPersister(SqlType.STRING, null);
		}
		return upgradeInfoPersister;
	}

	public JsonPersister(SqlType sqlType, Class<?>[] classes) {
		super(sqlType, classes);
	}

	@Override
	public Object parseDefaultString(FieldType fieldType, String defaultStr)
			throws SQLException {
		return "parseDefaultString";
	}

	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject)
			throws SQLException {
		IDownloadState state = (IDownloadState) javaObject;
		return state.getState().toString();
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results,
                                 int columnPos) throws SQLException {
		try {
			if(results==null){
				return BaseDownloadStateFactory.getDownloadNewState();
			}
			if(results.getString(columnPos)==null){
				return BaseDownloadStateFactory.getDownloadNewState();
			}
			int i = Integer.valueOf(results.getString(columnPos));
			BaseDownloadStateFactory.State state =BaseDownloadStateFactory.State.mapIntToValue(i);
			return 	BaseDownloadStateFactory.getDownloadState(state);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return BaseDownloadStateFactory.getDownloadNewState();
	}

}