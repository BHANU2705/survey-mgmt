package com.bps.dao;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.service.exceptions.BaseException;

public interface IBaseDAO {
	IBaseEntity create(IBaseEntity entity) throws BaseException;
	boolean update(IBaseEntity entity) throws BaseException;
	boolean delete(IBaseEntity entity) throws BaseException;
	IBaseEntity[] read() throws BaseException;
	IBaseEntity read(IBaseEntity entity) throws BaseException;
}
