package com.bps.model.validators;

import com.bps.persistence.tables.IBaseEntity;

public interface IValidator {
	IBaseEntity validate(IBaseEntity entity);
}
