package com.bps.service.api;

import com.bps.persistence.tables.IBaseEntity;

public interface IValidator {
	IBaseEntity validate(IBaseEntity entity);
}
