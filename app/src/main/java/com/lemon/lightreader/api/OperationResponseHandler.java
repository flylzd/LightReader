package com.lemon.lightreader.api;

import java.io.ByteArrayInputStream;

import org.apache.http.Header;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class OperationResponseHandler extends AsyncHttpResponseHandler {

	private Object[] args;

	public OperationResponseHandler(Looper looper, Object... args) {
		super(looper);
		this.args = args;
	}

	public OperationResponseHandler(Object... args) {
		this.args = args;
	}

	@Override
	public void onFailure(int code, Header[] headers, byte[] responseBytes, Throwable throwable) {
		onFailure(code, throwable.getMessage(), args);
	}

	public void onFailure(int code, String errorMessage, Object[] responseBytes) {
	}

	@Override
	public void onSuccess(int code, Header[] headers, byte[] responseBytes) {
		try {
			onSuccess(code, new ByteArrayInputStream(responseBytes), args);
		} catch (Exception e) {
			e.printStackTrace();
			onFailure(code, e.getMessage(), args);
		}
	}

	public void onSuccess(int code, ByteArrayInputStream is, Object[] responseBytes)
			throws Exception {

	}
}
