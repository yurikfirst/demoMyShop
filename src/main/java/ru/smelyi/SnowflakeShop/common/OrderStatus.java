package ru.smelyi.SnowflakeShop.common;

public enum OrderStatus {
	CREATED(1),
	EXECUTING(2),
	EXECUTED(3),
	CANCELED(4)
	;

	private final int code;

	OrderStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
