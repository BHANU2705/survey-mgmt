package com.bps.service.api;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.service.exceptions.BaseException;

public interface IValidator {
	IBaseEntity validate(IBaseEntity entity) throws BaseException;
}
