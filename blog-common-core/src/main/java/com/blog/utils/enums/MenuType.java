package com.blog.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型：目录、菜单、按钮
 * 
 * @author ty
 */
@Getter
@AllArgsConstructor
public enum MenuType {
	FOLDER(0, "目录"), MENU(1, "菜单"), BUTTON(2, "按钮");

	private final int value;
	private final String description;

	public static MenuType find(int code) {
		for (MenuType value : MenuType.values()) {
			if (code == value.getValue()) {
				return value;
			}
		}
		return null;
	}
}
