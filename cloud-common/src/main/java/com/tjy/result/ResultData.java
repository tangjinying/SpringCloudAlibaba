package com.tjy.result;

import lombok.Data;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 16:26
 * @Version 1.0
 */
@Data
public class ResultData<T> {
    /**
     * 结果状态 ,正常响应200，其他状态码都为失败
     */
    private int status;
    private String message;
    private T data;
    private boolean success;
    private long timestamp;

    public static ResultData success(Object o){
        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.setMessage("成功");
        resultData.setData(o);
        return resultData;
    }

    public static ResultData success(){
        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.setMessage("成功");
        return resultData;
    }

    public static ResultData fail(){
        ResultData resultData = new ResultData();
        resultData.setSuccess(false);
        resultData.setMessage("失败");
        return resultData;
    }

    public static ResultData fail(String message){
        ResultData resultData = new ResultData();
        resultData.setSuccess(false);
        resultData.setMessage(message);
        return resultData;
    }

	public static ResultData fail(int status,String message){
		ResultData resultData = new ResultData();
		resultData.setSuccess(false);
		resultData.setStatus(status);
		resultData.setMessage(message);
		return resultData;
	}
}