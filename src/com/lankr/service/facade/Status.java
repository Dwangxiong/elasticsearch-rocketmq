package com.lankr.service.facade;

public enum Status {
	NOT_FOUND {
		@Override
		public String message() {
			return "not found";
		}
	},
	SUCCESS {
		@Override
		public String message() {
			return "success";
		}
	},
	NO_VALUE {
		@Override
		public String message() {
			return "no value";
		}
	},
	STATBLE, UNSTABLE, NO_PERMISSION {
		@Override
		public String message() {
			return "permission denied";
		}
	},
	FAILURE {
		@Override
		public String message() {
			// TODO Auto-generated method stub
			return "failure";
		}
	},
	NOT_ACTIVE, IN_USE {
		@Override
		public String message() {
			return "in use";
		}
	},
	PARAM_ERROR {
		@Override
		public String message() {
			return "param invalid";
		}
	},
	SAVE_ERROR {
		@Override
		public String message() {
			return "save error";
		}
	},
	ERROR {
		@Override
		public String message() {
			// TODO Auto-generated method stub
			return "error";
		}
	},
	USER_NOT_FOUND {
		@Override
		public String message() {
			return "user not found";
		}
	},
	SUBMIT_REPEAT {
		@Override
		public String message() {
			return "repeated submission";
		}
	},
	OPERATION_FAST {
		@Override
		public String message() {
			// TODO Auto-generated method stub
			return "操作频率过快";
		}
	};

	public String message() {
		return "unknow";
	}
}
