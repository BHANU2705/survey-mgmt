package com.bps.dao;

import com.bps.persistence.tables.IBaseEntity;

public interface IBaseDAO {
	IBaseEntity create(IBaseEntity entity);
	boolean update(IBaseEntity entity);
	boolean delete(IBaseEntity entity);
	IBaseEntity[] read();
	IBaseEntity read(IBaseEntity entity);
}
