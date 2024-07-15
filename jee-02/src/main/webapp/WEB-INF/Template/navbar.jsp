<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
<% if(!request.getRequestURI().contains("user") && !request.getRequestURI().contains("admin")) { %>
<a href="login">Area Riservata</a> <%} %>

<% if(request.getRequestURI().contains("user") || request.getRequestURI().contains("admin")) { %>
<a href="/jee-02">Torna in Home Page</a><%} %>

<% if(request.getRequestURI().contains("admin")) { %>
<a href="admin?out">Logout</a> <%} %>

<% if(request.getRequestURI().contains("user")) { %>
<a href="user?out">Logout</a><%} %>
</div>