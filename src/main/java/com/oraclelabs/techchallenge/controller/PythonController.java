package com.oraclelabs.techchallenge.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oraclelabs.techchallenge.model.Code;
import com.oraclelabs.techchallenge.service.PythonProcessService;

@RestController
public class PythonController {

	@Autowired
	PythonProcessService pythonProcessService;

	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public String execute(@RequestBody Code code, HttpSession session) {
		try {
			String sessionId = session.getId();
			System.out.println(sessionId);
			String outpu = pythonProcessService.runCode(code.getCode(), sessionId);
			return outpu;

		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

}
