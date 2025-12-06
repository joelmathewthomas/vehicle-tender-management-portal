<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vtmp.home.admin.location.LocationBean"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Locations List</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/content.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/table.css" />
    <link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
      rel="stylesheet"
    />
  </head>

  <body>
    <div id="root">
		<%@ include file="../../jsp/include/header.jsp"%>
      <div class="content top">
        <div class="table-container">
          <h2>Locations List</h2>
          <div class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>Location ID</th>
                  <th>Location Name</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
              <%
              List<LocationBean> locations = (List<LocationBean>) request.getAttribute("locations");
            	
             	
              for (LocationBean l : locations) {
              %>
                <tr id="<%= "r" + l.getLocation_id()%>">
                  <td><%= l.getLocation_id() %></td>
                  <td><%= l.getLocation_name() %></td>
                  <td><a class="delete" href="${ctxPth}/admin/location/delete?lid=<%= l.getLocation_id() %>">Delete</a></td>
                </tr>
              <% } %>

                <tr>
                  <td>?</td>

                  <td>
                    <form
                      id="form"
                      method="POST"
                      action="${ctxPth}/admin/location/add"
                    >
                      <input
                        name="location_name"
                        type="text"
                        placeholder="Location Name"
                        pattern="^[A-Za-z]+(?:[ .' -][A-Za-z]+)*$"
                        title="Only letters allowed."
                        required
                      />
                    </form>
                  </td>

                  <td>
                    <button type="submit" form="form">Add</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
