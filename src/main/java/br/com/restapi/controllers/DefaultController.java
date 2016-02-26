package br.com.restapi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class DefaultController {
	
	protected void addPaginationLinks(HttpServletResponse res, HttpServletRequest req, Page page) {
		StringBuilder builder = new StringBuilder();
		builder.append(req.getRequestURL()).append("?page=0").append("&max=").append(page.getNumberOfElements()).append("; rel=\"first\"");
		builder.append(req.getRequestURL()).append("?page=").append(page.getNumber()+1).append("&max=").append(page.getNumberOfElements()).append("; rel=\"next\"");
		builder.append(req.getRequestURL()).append("?page=").append(page.getTotalPages()).append("&max=").append(page.getNumberOfElements()).append("; rel=\"last\"");
		res.addHeader(HttpHeaders.LINK, builder.toString());
		res.addHeader("X-Total-Count", String.valueOf(page.getTotalElements()));
	}

}
