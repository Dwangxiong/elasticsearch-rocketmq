package com.lankr.vo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.lankr.dennisit.util.Tools;
import com.lankr.service.facade.Status;

public class BaseAPIModel {

	protected static Gson gson = new GsonBuilder().disableHtmlEscaping()
			.create();

	private String status = Status.FAILURE.message();
	private int code = CodeConstant.OK;
	public long access_time = System.currentTimeMillis();
	private String message;
	private String toast;

	public String getToast() {
		return toast;
	}

	public void setToast(String toast) {
		this.toast = toast;
	}

	private String suggestion;

	// 用户提示客户端的处理工作
	// public void addCode(int code) {
	// if (action_code == null) {
	// action_code = new ArrayList<Integer>(1);
	// }
	// action_code.add(code);
	// }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatus(Status status) {
		if (status != null)
			setStatus(status.message());
	}

	public String toJSON() {
		return gson.toJson(this);
	}

	/*
	 * public String toXML() { // need implements return ""; }
	 */

	



	public static String makeWrappedSuccessDataJson(Object data) {
		return makeWrappedSuccessBaseAPIModel(data).toJSON();
	}

	public static BaseAPIModel makeWrappedSuccessBaseAPIModel(Object data) {
		return new CaseTmp(data);
	}

	private static class CaseTmp extends BaseAPIModel {
		CaseTmp(Object obj) {
			setStatus(Status.SUCCESS);
			data = obj;
		}

		Object data;
	}

	public String notNullString(String val) {
		return Tools.nullValueFilter(val);
	}

	public static final class CodeConstant {

		/***
		 * 无需任何操作
		 */
		public static final Integer OK = 0;

		/***
		 * tv端需要重新登录
		 */
		public static final Integer TV_RELOGIN = 1;

		/***
		 * 用户需要充值
		 */
		public static final Integer TV_RECHARGE = 2;

		/***
		 * 需要验证码
		 */
		public static final Integer VALIDATE_CODE_NEEDED = 3;

	}

}
