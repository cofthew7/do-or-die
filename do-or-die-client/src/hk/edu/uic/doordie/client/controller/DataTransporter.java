package hk.edu.uic.doordie.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import hk.edu.uic.doordie.client.model.vo.Todo;
import hk.edu.uic.doordie.client.model.vo.User;

public class DataTransporter {
	public User signup(String email, String password) {
		String url = "http://10.0.2.2:8080/do-or-die-server/Register";

		// 通过HttpClient方式连接
		HttpPost httpRequest = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();

		// 设置请求参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 将设置参数添加入请求列表
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		// 返回值
		User user = new User();
		
		try {

			// 对请求参数编码
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

			// 发送请求
			httpRequest.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			// JSON流接收器
			StringBuilder sb = new StringBuilder();

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// 接收返回对象
				HttpEntity responseEntity = httpResponse.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(responseEntity.getContent()));

				String line = null;
				if ((line = reader.readLine()) != null) {
					if (!line.equals("false")) {
						System.out.println(line);
						sb.append(line + "\n");
					} else {
						user = null;
					}
				}
				reader.close();

			}

			// 调用JSON解析器对JSON流进行解析
			if (sb.toString() != "") {
				JSONParser jp = new JSONParser();
				user = jp.toUser(sb.toString());
				return user;
			} else {
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.print("客户端请求编码出错" + "\n");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.print("客户端传输请求发生错误" + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("输入输出流出错" + "\n");
		}

		return user;
	}

	public Map<User, List<Todo>> signin(String email, String password) {
		String url = "http://10.0.2.2:8080/do-or-die-server/Login";

		// 通过HttpClient方式连接
		HttpPost httpRequest = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();

		// 设置请求参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 将设置参数添加入请求列表
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		// 返回值
		Map<User, List<Todo>> myInfoAndTodos = new HashMap<User, List<Todo>>();
		
		try {

			// 对请求参数编码
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

			// 发送请求
			httpRequest.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			// JSON流接收器
			StringBuilder sb = new StringBuilder();

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// 接收返回对象
				HttpEntity responseEntity = httpResponse.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(responseEntity.getContent()));

				String line = null;
				if ((line = reader.readLine()) != null) {
					if (!line.equals("false")) {
						System.out.println(line);
						sb.append(line + "\n");
					} else {
						myInfoAndTodos = null;
					}
				}
				reader.close();

			}

			// 调用JSON解析器对JSON流进行解析
			if (sb.toString() != "") {
				JSONParser jp = new JSONParser();
				myInfoAndTodos = jp.toUserAndTodos(sb.toString());
				return myInfoAndTodos;
			} else {
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.print("客户端请求编码出错" + "\n");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.print("客户端传输请求发生错误" + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("输入输出流出错" + "\n");
		}

		return myInfoAndTodos;
	}

	public List<User> getFriendList(int myId) {
		String url = "http://10.0.2.2:8080/do-or-die-server/Login";

		// 通过HttpClient方式连接
		HttpPost httpRequest = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();

		// 设置请求参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 将设置参数添加入请求列表
		params.add(new BasicNameValuePair("myId", String.valueOf(myId)));

		// 返回值
		List<User> friendList = new LinkedList<User>();
		
		try {

			// 对请求参数编码
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

			// 发送请求
			httpRequest.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			// JSON流接收器
			StringBuilder sb = new StringBuilder();

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// 接收返回对象
				HttpEntity responseEntity = httpResponse.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(responseEntity.getContent()));

				String line = null;
				if ((line = reader.readLine()) != null) {
					if (!line.equals("false")) {
						System.out.println(line);
						sb.append(line + "\n");
					} else {
						friendList = null;
					}
				}
				reader.close();

			}

			// 调用JSON解析器对JSON流进行解析
			if (sb.toString() != "") {
				JSONParser jp = new JSONParser();
				friendList = jp.toFriendList(sb.toString());
				return friendList;
			} else {
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.print("客户端请求编码出错" + "\n");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.print("客户端传输请求发生错误" + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("输入输出流出错" + "\n");
		}

		return friendList;
	}
}
