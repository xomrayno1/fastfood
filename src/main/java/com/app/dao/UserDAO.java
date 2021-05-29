package com.app.dao;

import com.app.entity.Users;

public interface UserDAO<E> extends BaseDAO<E> {
	Users findByUsername(String username);
}
